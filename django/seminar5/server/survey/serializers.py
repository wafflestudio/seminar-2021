from rest_framework import serializers

from survey.models import OperatingSystem, SurveyResult
from user.serializers import UserSerializer


class SurveyResultSerializer(serializers.ModelSerializer):
    os = serializers.SerializerMethodField()
    user = serializers.SerializerMethodField()
    os_name = serializers.CharField(write_only=True)
    timestamp = serializers.DateTimeField(read_only=True)

    class Meta:
        model = SurveyResult
        fields = (
            'id',
            'os',
            'user',
            'python',
            'rdb',
            'programming',
            'major',
            'grade',
            'backend_reason',
            'waffle_reason',
            'say_something',
            'timestamp',
            'os_name'
        )

    def get_os(self, survey):
        return OperatingSystemSerializer(survey.os, context=self.context).data

    def get_user(self, survey):
        if survey.user:
            return UserSerializer(survey.user, context=self.context).data
        return None

    def create(self, validated_data):
        os, created = OperatingSystem.objects.get_or_create(name=validated_data.pop('os_name'))
        validated_data['os'] = os
        validated_data['user'] = self.context['request'].user
        return super().create(validated_data)


class OperatingSystemSerializer(serializers.ModelSerializer):

    class Meta:
        model = OperatingSystem
        fields = (
            'id',
            'name',
            'description',
            'price',
        )