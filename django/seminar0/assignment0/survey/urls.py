from django.urls import include, path
from django.views.decorators.http import require_http_methods

from survey import views

urlpatterns = [
    path('results/', views.get_survey_results, name="get_surveys"),
    path('results/<survey_id>/', views.get_survey, name="get_survey"),
    path('os/', views.get_os_results, name="get_os"),
    path('os/<operatingsystem_id>/', require_http_methods(['GET', ])(views.get_os), name="get_os_djlas"),
]
