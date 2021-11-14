"""waffle_backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from rest_framework.permissions import AllowAny
from django.conf.urls import url
from django.contrib import admin
from django.urls import include, path
from django.conf import settings

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/v1/', include('survey.urls')),
    path('api/v1/', include('user.urls')),
    path('api/v1/', include('seminar.urls')),
]

schema_url_patterns = [
    path('admin/', admin.site.urls),
    path('api/v1/', include('survey.urls')),
    path('api/v1/', include('user.urls')),
    path('api/v1/', include('seminar.urls')),
]

if settings.DEBUG_TOOLBAR:
    import debug_toolbar

    urlpatterns += [
        url(r'^__debug__/', include(debug_toolbar.urls)),
    ]

urlpatterns += [path('api-auth/', include('rest_framework.urls')), ]
