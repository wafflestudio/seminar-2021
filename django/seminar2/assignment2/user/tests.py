from django.test import TestCase


# Create your tests here.
from rest_framework import status

from seminar.models import ParticipantProfile, InstructorProfile
from user.models import User
from user.test_user import UserFactory


class UserTestCase(TestCase):

    @classmethod
    def setUpTestData(cls):
        cls.user = UserFactory(
            email='test@test.com',
            username='test',
            password='test',
            is_participant=True,
            is_instructor=True
        )
        cls.instructor = UserFactory(
            email='instructor@test.com',
            username='test',
            password='test',
            is_instructor=True
        )

    def test_signup(self):

        data = {'username': 'test', 'role': 'participant', 'email': 'signup@test.com', 'password': 'password'}
        response = self.client.post('/api/v1/signup/', data=data)

        self.assertContains(response, 'token', status_code=status.HTTP_201_CREATED)
        self.assertEqual(response.data['user'], 'signup@test.com')

    def test_login(self):
        data = {'email': 'test@test.com', 'password': 'test'}
        response = self.client.post('/api/v1/login/', data=data)

        self.assertContains(response, 'token', status_code=status.HTTP_200_OK)
        self.assertTrue(response.data['success'])

    def test_profile_get(self):

        client = self.client
        client.force_login(self.user)

        response = client.get('/api/v1/user/me/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['email'], 'test@test.com')
        self.assertTrue(response.data['participant'] and response.data['instructor'])

        user = User.objects.get(email='instructor@test.com')
        response = client.get(f'/api/v1/user/{user.id}/')
        self.assertEqual(response.data['email'], 'instructor@test.com')

    def test_profile_update(self):
        user = User.objects.get(email='test@test.com')

        client = self.client
        client.force_login(user)

        response = client.put('/api/v1/user/me/',
                              data={'university': '서울대학교', 'company': '와플스튜디오'},
                              content_type='application/json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['participant']['university'], '서울대학교')
        self.assertTrue(response.data['instructor']['company'], '와플스튜디오')

    def test_participant(self):
        user = User.objects.get(email='instructor@test.com')

        client = self.client
        client.force_login(user)

        response = client.post('/api/v1/user/participant/',
                              data={'university': '연세대학교'},
                              content_type='application/json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['participant']['university'], '연세대학교')




