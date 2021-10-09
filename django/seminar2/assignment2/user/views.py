import rest_framework
from django.contrib.auth import authenticate, login, logout, get_user_model
from django.db import IntegrityError
from rest_framework import status, viewsets, permissions
from rest_framework.views import APIView
from rest_framework_jwt.serializers import JSONWebTokenSerializer
from rest_framework_jwt.views import ObtainJSONWebToken
from rest_framework.decorators import action
from rest_framework.response import Response
from user.serializers import UserSerializer, UserLoginSerializer, UserCreateSerializer, CreateParticipantProfileService

User = get_user_model()


class UserSignUpView(APIView):
    permission_classes = (permissions.AllowAny, )

    def post(self, request, *args, **kwargs):

        serializer = UserCreateSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        # FIXME: IntegrityError 구현을 보면, 모든 DB Error 또한 정합성 에러인것처럼 내려보내질 우려가 있습니다.
        try:
            user, jwt_token = serializer.save()
        except IntegrityError:
            return Response(status=status.HTTP_409_CONFLICT, data='이미 존재하는 유저 이메일입니다.')

        return Response({'user': user.email, 'token': jwt_token}, status=status.HTTP_201_CREATED)


class UserLoginView(APIView):
    permission_classes = (permissions.AllowAny, )

    def post(self, request):

        serializer = UserLoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        token = serializer.validated_data['token']

        return Response({'success': True, 'token': token}, status=status.HTTP_200_OK)


class UserViewSet(viewsets.GenericViewSet):

    permission_classes = (permissions.IsAuthenticated, )
    serializer_class = UserSerializer
    queryset = User.objects.all()

    def update(self, request, pk=None):
        if pk != 'me':
            return Response(status=status.HTTP_403_FORBIDDEN, data='다른 유저 정보를 수정할 수 없습니다.')

        user = request.user

        serializer = self.get_serializer(user, data=request.data, partial=True)
        serializer.is_valid(raise_exception=True)
        serializer.update(user, serializer.validated_data)
        return Response(status=status.HTTP_200_OK, data=self.get_serializer(user).data)

    def retrieve(self, request, pk=None):

        if request.user.is_anonymous:
            return Response(status=status.HTTP_403_FORBIDDEN, data='먼저 로그인 하세요.')

        user = request.user if pk == 'me' else self.get_object()
        return Response(self.get_serializer(user).data)

    @action(detail=False, methods=['POST'])
    def participant(self, request):
        service = CreateParticipantProfileService(data={'university': request.data.get('university'), 'accepted': request.data.get('accepted')},
                                                  partial=True, context={'request': request})
        status_code, data = service.execute()
        return Response(status=status_code, data=data)


class LogInView(ObtainJSONWebToken):

    # 이렇게만 해도 로그인 기능 수행할 수 있음.
    # api/v1/easy_login
    pass
