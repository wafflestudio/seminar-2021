from django.utils import timezone
from rest_framework import serializers, status

from .models import ParticipantProfile, InstructorProfile, Seminar, UserSeminar


class UserRole:
    PARTICIPANT = 'participant'
    INSTRUCTOR = 'instructor'

    choices = (
        (PARTICIPANT, 'participant'),
        (INSTRUCTOR, 'instructor'),
    )


class ParticipantSerializer(serializers.ModelSerializer):

    id = serializers.SerializerMethodField()
    university = serializers.CharField(required=False, allow_null=True, default='')
    accepted = serializers.BooleanField(required=False, allow_null=True, default=True)

    class Meta:

        model = ParticipantProfile
        fields = (
            'id',
            'university',
            'accepted',
        )

    def get_id(self, instance):
        return instance.user.id

    def validate_university(self, value):
        return value or ''

    def validate_accepted(self, value):
        return value or True

    def create(self, validated_data):
        user = self.context.get('user')
        validated_data['user'] = user
        return super().create(validated_data)



class InstructorSerializer(serializers.ModelSerializer):

    id = serializers.SerializerMethodField()
    company = serializers.CharField(required=False, allow_null=True, default='')
    year = serializers.IntegerField(required=False, allow_null=True)

    class Meta:
        model = InstructorProfile
        fields = (
            'id',
            'company',
            'year',
        )

    def get_id(self, instance):

        return instance.user.id

    def validate_company(self, value):
        return value or ''

    def create(self, validated_data):
        user = self.context.get('user')
        validated_data['user'] = user
        return super().create(validated_data)


class SeminarSerializer(serializers.ModelSerializer):

    online = serializers.BooleanField(required=False, default=True)
    participants = serializers.SerializerMethodField()
    instructors = serializers.SerializerMethodField()

    class Meta:
        model = Seminar
        exclude = ('created_at', 'updated_at', )

    def get_participants(self, instance):

        participants = ParticipantSerializer(list(instance.user_seminars.filter(is_instructor=False)), many=True).data

        return participants

    def get_instructors(self, instance):

        instructors = InstructorSerializer(list(instance.user_seminars.filter(is_instructor=True)), many=True).data

        return instructors

    def create(self, validated_data):

        user = self.context['request'].user
        if not hasattr(user, 'instructor'):
            raise serializers.ValidationError('강사만 세미나를 등록할 수 있습니다.')

        seminar = super().create(validated_data)
        UserSeminar.objects.create(
            seminar=seminar,
            user=user,
            is_instructor=True
        )
        return seminar

    def update(self, instance, validated_data):

        capacity = validated_data.get('capacity')
        if capacity and instance.user_seminars.filter(is_active=True).count() > capacity:
            raise serializers.ValidationError('이미 들어찬 정원보다 적게는 줄일 수 없어요')
        super().update(instance, validated_data)


class SeminarViewSerializer(SeminarSerializer):

    participant_count = serializers.SerializerMethodField()
    instructors = serializers.SerializerMethodField()

    class Meta:
        model = Seminar
        fields = (
            'id',
            'name',
            'instructors',
            'participant_count'
        )

    def get_participant_count(self, instance):

        return instance.user_seminars.filter(is_instructor=False, is_active=True).count()


class ParticipantSeminarSerializer(serializers.ModelSerializer):

    id = serializers.SerializerMethodField()
    name = serializers.SerializerMethodField()
    joined_at = serializers.DateTimeField(source='created_at')

    class Meta:
        model = UserSeminar
        fields = (
            'id',
            'name',
            'joined_at',
            'is_active',
            'dropped_at'
        )

    def get_id(self, instance):
        return instance.seminar.id

    def get_name(self, instance):
        return instance.seminar.name


class InstructorSeminarSerializer(serializers.ModelSerializer):

    id = serializers.SerializerMethodField()
    name = serializers.SerializerMethodField()
    joined_at = serializers.DateTimeField(source='created_at')

    class Meta:
        model = UserSeminar
        fields = (
            'id',
            'name',
            'joined_at'
        )

    def get_id(self, instance):
        return instance.seminar.id

    def get_name(self, instance):
        return instance.seminar.name


class DropSeminarService(serializers.Serializer):

    def execute(self):

        user = self.context['request'].user
        seminar_id = self.context.get('seminar_id')
        seminar = Seminar.objects.get_or_none(id=seminar_id)

        if not seminar:
            return status.HTTP_404_NOT_FOUND, '세미나가 없습니다.'

        if user.user_seminars.filter(seminar_id=seminar_id, is_instructor=True).exists():
            return status.HTTP_403_FORBIDDEN, '강사는 드랍할 수 없어요'

        # FIXME: common/models.py:6 참고
        target = user.user_seminars.get_or_none(seminar_id=seminar_id)

        if not target:
            return status.HTTP_200_OK, '해당 세미나에 참여 중이지 않습니다.'

        target.is_active = False
        target.dropped_at = timezone.now()
        target.save()

        return status.HTTP_200_OK, SeminarSerializer(seminar).data


class RegisterSeminarService(serializers.Serializer):

    role = serializers.ChoiceField(choices=UserRole.choices)

    def execute(self):

        self.is_valid()
        seminar_id = self.context.get('seminar_id')
        role = self.validated_data['role']
        user = self.context.get('request').user
        seminar = Seminar.objects.get_or_none(id=seminar_id)

        if not seminar:
            return status.HTTP_404_NOT_FOUND, '그런 세미나는 없습니다.'

        if not hasattr(user, UserRole.PARTICIPANT):
            return status.HTTP_403_FORBIDDEN, f'{role} 프로필이 없습니다.'

        if role == UserRole.PARTICIPANT:
            if not user.participant.accepted:
                return status.HTTP_403_FORBIDDEN, '수강생 등록 승인이 되지 않았습니다.'

            if seminar.user_seminars.filter(is_instructor=False, is_active=True).count() == seminar.capacity:
                return status.HTTP_400_BAD_REQUEST, '정원이 가득 찼습니다.'

        if seminar.user_seminars.get_or_none(user=user):
            return status.HTTP_400_BAD_REQUEST, '이미 참여중입니다.'

        UserSeminar.objects.create(
            seminar=seminar,
            user=user,
            is_instructor=(role == UserRole.INSTRUCTOR),
        )
        return status.HTTP_201_CREATED, SeminarSerializer(seminar).data








