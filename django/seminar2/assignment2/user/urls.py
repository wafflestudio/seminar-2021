from django.urls import path, include
from rest_framework.routers import SimpleRouter
from .views import UserViewSet, UserLoginView, UserSignUpView

from survey import views
router = SimpleRouter()
router.register('user', UserViewSet, basename='user')  # /api/v1/user/

urlpatterns = [
    path('signup/', UserSignUpView.as_view(), name='signup'),  # /api/v1/signup/
    path('login/', UserLoginView.as_view(), name='login'),  # /api/v1/login/
    path('', include(router.urls), name='auth-user')
]
