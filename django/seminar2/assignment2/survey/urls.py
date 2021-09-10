from django.urls import include, path
from rest_framework.routers import SimpleRouter
from survey.views import OperatingSystemViewSet, SurveyResultViewSet, top_50

app_name = 'survey'

router = SimpleRouter()
router.register('survey', SurveyResultViewSet, basename='survey')
router.register('os', OperatingSystemViewSet, basename='os')

urlpatterns = [
    path('', include(router.urls)),
    path('template', top_50)
]
