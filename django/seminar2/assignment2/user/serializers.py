from abc import ABC
from django.contrib.auth import get_user_model, authenticate
from django.contrib.auth.hashers import make_password
from django.contrib.auth.models import update_last_login
from django.db import transaction, models
from rest_framework import serializers, status
from rest_framework_jwt.settings import api_settings

from seminar.models import ParticipantProfile
from seminar.serializers import InstructorSerializer, ParticipantSerializer, InstructorSeminarSerializer, \
    ParticipantSeminarSerializer

# 토큰 사용을 위한 기본 세팅
User = get_user_model()
JWT_PAYLOAD_HANDLER = api_settings.JWT_PAYLOAD_HANDLER
JWT_ENCODE_HANDLER = api_settings.JWT_ENCODE_HANDLER


# [ user -> jwt_token ] function
def jwt_token_of(user):
    payload = JWT_PAYLOAD_HANDLER(user)
    jwt_token = JWT_ENCODE_HANDLER(payload)
    return jwt_token


class UserRole:
    PARTICIPANT = 'participant'
    INSTRUCTOR = 'instructor'

    choices = (
        (PARTICIPANT, 'participant'),
        (INSTRUCTOR, 'instructor'),
    )


class UserCreateSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)
    username = serializers.CharField(required=True)
    password = serializers.CharField(required=True)
    first_name = serializers.CharField(required=False)
    last_name = serializers.CharField(required=False)

    # added
    role = serializers.ChoiceField(choices=UserRole.choices, required=True)
    university = serializers.CharField(required=False)
    accepted = serializers.BooleanField(required=False)
    company = serializers.CharField(required=False)
    year = serializers.IntegerField(required=False)

    def build_profile(self, user, validated_data):
        role = validated_data['role']
        profile, serializer = {}, None
        if role == UserRole.PARTICIPANT:
            serializer = ParticipantSerializer
            university = validated_data.get('university')
            accepted = validated_data.get('accepted')
            profile.update({'university': university, 'accepted': accepted})
        elif role == UserRole.INSTRUCTOR:
            serializer = InstructorSerializer
            company = validated_data.get('company')
            year = validated_data.get('year')
            profile.update({'company': company, 'year': year})

        sz = serializer(data=profile, partial=True, context={'user': user})
        sz.is_valid(raise_exception=True)
        sz.save()
        return profile

    def validate(self, data):
        first_name = data.get('first_name')
        last_name = data.get('last_name')
        year = data.get('year')
        if bool(first_name) ^ bool(last_name):
            raise serializers.ValidationError("성과 이름 중에 하나만 입력할 수 없습니다.")
        if first_name and last_name and not (first_name.isalpha() and last_name.isalpha()):
            raise serializers.ValidationError("이름에 숫자가 들어갈 수 없습니다.")
        if year and year <= 0:
            raise serializers.ValidationError("year; 양의 정수만 가능합니다.")
        return data

    # FIXME: build_profile 에서 문제가 생겼을 때, 생성된 user를 삭제해주어야 합니다.
    #  이런 경우에는 atomic 옵션을 부여하여 트랜잭션을 관리해줄 수 있겠습니다.
    @transaction.atomic
    def create(self, validated_data):
        user = User.objects.create_user(
            email=validated_data.pop('email'),
            username=validated_data.pop('username'),
            password=validated_data.pop('password'),
            first_name=validated_data.pop('first_name', ''),
            last_name=validated_data.pop('last_name', '')
        )
        self.build_profile(user, validated_data)
        return user, jwt_token_of(user)


class UserLoginSerializer(serializers.Serializer):
    email = serializers.CharField(max_length=64, required=True)
    password = serializers.CharField(max_length=128, write_only=True)
    token = serializers.CharField(max_length=255, read_only=True)

    def validate(self, data):
        email = data.get('email', None)
        password = data.get('password', None)
        user = authenticate(email=email, password=password)

        if user is None:
            raise serializers.ValidationError("이메일 또는 비밀번호가 잘못되었습니다.")

        update_last_login(None, user)
        return {
            'email': user.email,
            'token': jwt_token_of(user)
        }


class UserSerializer(serializers.ModelSerializer):
    participant = serializers.SerializerMethodField()
    instructor = serializers.SerializerMethodField()

    # added
    university = serializers.CharField(required=False, write_only=True)
    company = serializers.CharField(required=False, write_only=True)
    year = serializers.IntegerField(required=False, write_only=True)

    class Meta:
        model = User
        # Django 기본 User 모델에 존재하는 필드 중 일부
        fields = (
            'id',
            'username',
            'email',
            'password',
            'last_login',  # 가장 최근 로그인 시점
            'date_joined',  # 가입 시점
            'first_name',
            'last_name',
            'participant',
            'instructor',
            'university',
            'company',
            'year'
        )
        extra_kwargs = {'password': {'write_only': True}}

    def validate_year(self, value):

        if value <= 0:
            raise serializers.ValidationError('year; 양의 정수만 가능합니다.')

        return value

    def validate_university(self, value):

        return value or ''

    def validate_company(self, value):

        return value or ''

    def get_participant(self, instance):

        if hasattr(instance, 'participant'):
            profile = instance.participant
        else:
            return None
        data = ParticipantSerializer(profile).data
        seminars = ParticipantSeminarSerializer(list(instance.user_seminars.filter(is_instructor=False)),
                                                many=True).data
        data['seminars'] = seminars
        return data

    def get_instructor(self, instance):

        if hasattr(instance, 'instructor'):
            profile = instance.instructor
        else:
            return None
        data = InstructorSerializer(profile).data

        charge = instance.user_seminars.filter(is_instructor=True).first()
        data['charge'] = InstructorSeminarSerializer(charge).data if charge else None
        return data

    def create(self, validated_data):
        user = super().create(validated_data)
        return user

    def update(self, user, validated_data):

        university = validated_data.get('university')
        company, year = validated_data.get('company'), validated_data.get('year')

        if hasattr(user, 'instructor'):
            profile = user.instructor
            profile.company = company
            profile.year = year
            profile.save(update_fields=['company', 'year'])

        if hasattr(user, 'participant'):
            profile = user.participant
            profile.university = university
            profile.save(update_fields=['university'])

        super().update(user, validated_data)
        return user

class CreateParticipantProfileService(serializers.Serializer):

    university = serializers.CharField(allow_null=True)

    def validate_university(self, value):
        return value or ''

    def execute(self):
        self.is_valid()
        user = self.context['request'].user
        university = self.validated_data['university']

        if hasattr(user, 'participant'):
            return status.HTTP_409_CONFLICT, '이미 수강생으로 등록되어 있는 사용자입니다.'

        ParticipantProfile.objects.create(university=university, user=user)
        return status.HTTP_201_CREATED, UserSerializer(user).data


