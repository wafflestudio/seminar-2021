from django.contrib import admin
from .models import SurveyResult, OperatingSystem
# Register your models here.
admin.site.register(SurveyResult)
admin.site.register(OperatingSystem)