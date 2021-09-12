from abc import ABC
from django.contrib.auth import get_user_model, authenticate
from django.contrib.auth.hashers import make_password
from django.contrib.auth.models import update_last_login
from rest_framework import serializers
from rest_framework_jwt.settings import api_settings

# 토큰 사용을 위한 기본 세팅
User = get_user_model()
JWT_PAYLOAD_HANDLER = api_settings.JWT_PAYLOAD_HANDLER
JWT_ENCODE_HANDLER = api_settings.JWT_ENCODE_HANDLER


# [ user -> jwt_token ] function
def jwt_token_of(user):
    payload = JWT_PAYLOAD_HANDLER(user)
    jwt_token = JWT_ENCODE_HANDLER(payload)
    return jwt_token


class UserCreateSerializer(serializers.Serializer):

    email = serializers.EmailField(required=True)
    username = serializers.CharField(required=True)
    password = serializers.CharField(required=True)

    def validate(self, data):
        first_name = data.get('first_name')
        last_name = data.get('last_name')
        if bool(first_name) ^ bool(last_name):
            raise serializers.ValidationError("성과 이름 중에 하나만 입력할 수 없습니다.")
        if first_name and last_name and not (first_name.isalpha() and last_name.isalpha()):
            raise serializers.ValidationError("이름에 숫자가 들어갈 수 없습니다.")
        return data

    def create(self, validated_data):
        # TODO (1. 유저 만들고 (ORM) , 2. 비밀번호 설정하기; 아래 코드를 수정해주세요.)
        user = None
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
            'last_name'
        )
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        user = super().create(validated_data)
        return user