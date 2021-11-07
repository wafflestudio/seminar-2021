import json
from django.http import JsonResponse, HttpResponseNotAllowed, HttpResponseNotFound, HttpResponseBadRequest
from django.shortcuts import get_object_or_404
from django.views.decorators.http import require_http_methods

from survey.models import SurveyResult, OperatingSystem
from survey.serializers import serialize_survey_result, serialize_os


def get_survey_results(request):

    if request.method == 'GET':

        if param := request.GET.get('os'):
            # os validation
            try:
                queryset = SurveyResult.objects.filter(os__name=param)
            except OperatingSystem.DoesNotExist:
                return HttpResponseBadRequest("Bad Request!")
        else:
            queryset = SurveyResult.objects.all()

        survey_results = list(map(lambda result: serialize_survey_result(result), queryset))
        return JsonResponse({"surveys": survey_results}, status=200)

    else:
        return HttpResponseNotAllowed(['GET', ])


def get_survey(request, survey_id):
    if request.method == 'GET':
        survey = get_object_or_404(SurveyResult, id=survey_id)
        return JsonResponse(serialize_survey_result(survey))
    else:
        return HttpResponseNotAllowed(['GET', ])


@require_http_methods(['GET', ])
def get_os_results(request):
    objs = OperatingSystem.objects.all()
    result = list(map(lambda x: serialize_os(x), objs))
    return JsonResponse({"os": result})


def get_os(request, operatingsystem_id):
    try:
        obj = OperatingSystem.objects.get(id=operatingsystem_id)
    except OperatingSystem.DoesNotExist:
        return HttpResponseNotFound("검색하신 os가 없어요.")

    return JsonResponse(serialize_os(obj))

