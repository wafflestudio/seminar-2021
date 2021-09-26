from django.db import models
from django.contrib.auth import get_user_model

from common.models import BaseModel

User = get_user_model()


# Create your models here.
class Seminar(BaseModel):

    name = models.CharField(max_length=100, blank=False)
    capacity = models.IntegerField()
    count = models.IntegerField()
    time = models.TimeField()
    online = models.BooleanField(blank=True, default=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class UserSeminar(BaseModel):

    seminar = models.ForeignKey(Seminar, on_delete=models.CASCADE, related_name='user_seminars')
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name='user_seminars')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    is_instructor = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    dropped_at = models.DateTimeField(null=True)


class ParticipantProfile(BaseModel):

    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='participant')
    university = models.CharField(max_length=100, blank=True, default='')
    accepted = models.BooleanField(blank=True, default=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class InstructorProfile(BaseModel):

    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='instructor')
    company = models.CharField(max_length=100, blank=True)
    year = models.IntegerField(null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

