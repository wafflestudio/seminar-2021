from pathlib import Path

from datetime import datetime
from django.core.management.base import BaseCommand
from django.utils import timezone

from waffle_backend import settings
from survey.models import OperatingSystem, SurveyResult


def download_survey():
    # NOTE: if you run this command multiple times, rows of 'survey_surveyresult' table will be added repeatedly.
    #       to block that behavior, uncomment below lines.
    #
    # if SurveyResult.objects.all().exists():
    #     raise Exception("There already exists data. Perhaps you are downloading it multiple times")

    path = settings.BASE_DIR
    if not path:
        raise Exception("Please specify path of directory including 'example_surveyresult.tsv'!")
    tsv_file = f"{path}/example_surveyresult.tsv"

    OperatingSystem.objects.get_or_create(name='Windows', price=200000, description="Most favorite OS in South Korea")
    OperatingSystem.objects.get_or_create(name='MacOS', price=300000, description="Most favorite OS of Seminar Instructors")
    OperatingSystem.objects.get_or_create(name='Ubuntu (Linux)', price=0, description="Linus Benedict Torvalds")

    with open(tsv_file) as f:
        for idx, line in enumerate(f, start=1):
            if idx < 2:
                continue

            data = line.split('\t')

            operating_system, created = OperatingSystem.objects.get_or_create(name=data[1])
            t = SurveyResult.objects.create(timestamp=timezone.make_aware(datetime.strptime(data[0], '%Y-%m-%d %H:%M:%S')),
                                        os=operating_system, python=int(data[2]), rdb=int(data[3]),
                                        programming=int(data[4]), major=data[5], grade=data[6],
                                        backend_reason=data[7], waffle_reason=data[8], say_something=data[9])


class Command(BaseCommand):

    def handle(self, *args, **options):
        download_survey()
