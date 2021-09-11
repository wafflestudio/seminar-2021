from rest_framework import serializers

from django.contrib.auth.models import User


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

    def validate(self, data):
        first_name = data.get('first_name')
        last_name = data.get('last_name')
        if bool(first_name) ^ bool(last_name):
            raise serializers.ValidationError("성과 이름 중에 하나만 입력할 수 없습니다.")
        if first_name and last_name and not (first_name.isalpha() and last_name.isalpha()):
            raise serializers.ValidationError("이름에 숫자가 들어갈 수 없습니다.")
        return data

    def create(self, validated_data):
        print(validated_data)
        password = validated_data.pop('password')
        user = super().create(validated_data)
        user.set_password(password)
        user.save()
        return user


