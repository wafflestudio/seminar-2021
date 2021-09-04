from rest_framework import serializers

from survey.models import OperatingSystem, SurveyResult


class SurveyResultSerializer(serializers.ModelSerializer):
    os = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model = SurveyResult
        fields = (
            'id',
            'os',
            'python',
            'rdb',
            'programming',
            'major',
            'grade',
            'backend_reason',
            'waffle_reason',
            'say_something',
            'timestamp',
        )

    def get_os(self, survey):
        return OperatingSystemSerializer(survey.os, context=self.context).data


class OperatingSystemSerializer(serializers.ModelSerializer):

    class Meta:
        model = OperatingSystem
        fields = (
            'id',
            'name',
            'description',
            'price',
        )