from django.urls import include, path
from rest_framework.routers import SimpleRouter

from .views import query_practice, SeminarViewSet, UserSeminarView

router = SimpleRouter()
router.register('seminar', SeminarViewSet, basename='seminar')

urlpatterns = [
    path('', include(router.urls)),
    path('seminar/<seminar_id>/user', UserSeminarView.as_view()),
    path('query_practice/', query_practice)
]
