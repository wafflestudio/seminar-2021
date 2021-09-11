from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.db import IntegrityError
from django.views import View

from rest_framework import status, viewsets
from rest_framework.decorators import action
from rest_framework.response import Response

from user.serializers import UserSerializer


class UserViewSet(viewsets.GenericViewSet):
    serializer_class = UserSerializer
    queryset = User.objects.all()

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        try:
            user = serializer.save()
        except IntegrityError:
            return Response(status=status.HTTP_409_CONFLICT)

        # 가입했으니 바로 로그인 시켜주기
        login(request, user)
        return Response(self.get_serializer(user).data, status=status.HTTP_201_CREATED)

    # PUT /api/v1/user/login/
    @action(detail=False, methods=['PUT'])
    def login(self, request):
        username = request.data.get('username')
        password = request.data.get('password')

        user = authenticate(request, username=username, password=password)
        if user:
            login(request, user)
            return Response(self.get_serializer(user).data)
        # 존재하지 않는 사용자이거나 비밀번호가 틀린 경우
        return Response(status=status.HTTP_403_FORBIDDEN, data='아이디나 패스워드가 일치하지 않습니다.')

    def update(self, request, pk=None):

        if pk != 'me':
            return Response(status=status.HTTP_403_FORBIDDEN, data='다른 유저 정보를 수정할 수 없습니다.')

        user = request.user

        serializer = self.get_serializer(user, data=request.data, partial=True)
        serializer.is_valid(raise_exception=True)
        serializer.update(user, serializer.validated_data)
        return Response(status=status.HTTP_200_OK)

    @action(detail=False, methods=['POST'])
    def logout(self, request):
        logout(request)
        return Response(status=status.HTTP_200_OK)

    def retrieve(self, request, pk=None):

        if request.user.is_anonymous:
            return Response(status=status.HTTP_403_FORBIDDEN, data='먼저 로그인 하세요.')

        user = request.user if pk == 'me' else self.get_object()
        return Response(self.get_serializer(user).data)
