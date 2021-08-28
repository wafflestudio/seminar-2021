import json
from django.http import JsonResponse, HttpResponseNotAllowed
from django.shortcuts import get_object_or_404

from survey.models import SurveyResult
from survey.serializers import serialize_survey_result


def get_survey_results(request):
    if request.method == 'GET':
        params = request.GET.get('os')
        survey_results = list(map(lambda result: serialize_survey_result(result), SurveyResult.objects.all()))
        return JsonResponse({"surveys": survey_results}, status=200)
    else:
        return HttpResponseNotAllowed(['GET', ])


def get_survey(request, survey_id):
    if request.method == 'GET':
        survey = get_object_or_404(SurveyResult, id=survey_id)
        return JsonResponse(serialize_survey_result(survey))
    else:
        return HttpResponseNotAllowed(['GET', ])
