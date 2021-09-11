from django.shortcuts import get_object_or_404, render
from django.views.decorators.http import require_http_methods
from rest_framework import status, viewsets, permissions
from rest_framework.response import Response

from survey.serializers import OperatingSystemSerializer, SurveyResultSerializer
from survey.models import OperatingSystem, SurveyResult


class SurveyResultViewSet(viewsets.GenericViewSet):
    queryset = SurveyResult.objects.all()
    serializer_class = SurveyResultSerializer
    permission_classes = (permissions.IsAuthenticated(), )

    def get_permissions(self):
        if self.action in ('list', 'retrieve'):
            return (permissions.AllowAny(), )
        return self.permission_classes

    def list(self, request):
        surveys = self.get_queryset().select_related('os')
        return Response(self.get_serializer(surveys, many=True).data)

    def retrieve(self, request, pk=None):
        survey = get_object_or_404(SurveyResult, pk=pk)
        return Response(self.get_serializer(survey).data)

    def create(self, request):
        # copy makes request.data mutable
        data = request.data.copy()
        data.update(os_name=data.get('os'))

        serializer = self.get_serializer(data=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data, status=status.HTTP_201_CREATED)


class OperatingSystemViewSet(viewsets.GenericViewSet):
    queryset = OperatingSystem.objects.all()
    serializer_class = OperatingSystemSerializer

    def list(self, request):
        return Response(self.get_serializer(self.get_queryset(), many=True).data)

    def retrieve(self, request, pk=None):
        try:
            os = OperatingSystem.objects.get(id=pk)
        except OperatingSystem.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)
        return Response(self.get_serializer(os).data)


@require_http_methods('GET')
def top_50(request):

    surveys = SurveyResult.objects.all()[:50]
    return render(request, 'index.html', context={'surveys': surveys})
