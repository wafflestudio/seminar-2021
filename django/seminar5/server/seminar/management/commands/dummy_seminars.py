from pathlib import Path

from datetime import datetime
from django.core.management.base import BaseCommand
from django.utils import timezone

from django.conf import settings
from survey.models import OperatingSystem, SurveyResult

from user.test_user import UserFactory
from seminar.tests import SeminarFactory
from seminar.models import UserSeminar

import random

def dummy_seminars():

    for i in range(10):
        seminar = SeminarFactory(count=0, capacity=random.randint(1, 10), time=timezone.now())
        UserSeminar.objects.create(
            seminar=seminar,
            user=UserFactory(is_instructor=True),
            is_instructor=True
        )
        rand = random.randint(1, 4)
        for k in range(int(seminar.capacity / rand)):
            UserSeminar.objects.create(
                seminar=seminar,
                user=UserFactory(is_participant=True),
                is_active=True
            )


class Command(BaseCommand):

    def handle(self, *args, **options):
        dummy_seminars()
