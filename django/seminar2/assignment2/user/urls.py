from django.urls import path, include
from rest_framework.routers import SimpleRouter

from .views import UserViewSet

from survey import views

router = SimpleRouter()
router.register('user', UserViewSet, basename='user')  # /api/v1/user/

urlpatterns = [
    path('', include(router.urls), name='auth-user')
]
