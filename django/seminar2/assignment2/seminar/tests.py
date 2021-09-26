from django.test import TestCase
from django.utils import timezone

from factory.django import DjangoModelFactory
from rest_framework import status

from seminar.models import Seminar, UserSeminar
from user.test_user import UserFactory


class SeminarFactory(DjangoModelFactory):
    class Meta:
        model = Seminar


# Create your tests here.
class SeminarTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        cls.participant = UserFactory(email='student@test.com', is_participant=True)
        cls.instructor = UserFactory(email='instructor@test.com', is_instructor=True)
        cls.both = UserFactory(email='both@test.com', is_participant=True, is_instructor=True)
        cls.seminar = SeminarFactory(name='세미나', capacity=100, count=100, time=timezone.now().time())
        UserSeminar.objects.create(
            user=cls.instructor, seminar=cls.seminar, is_instructor=True
        )

    def setUp(self):
        self.post_data = {
            'name': '세미나',
            'capacity': 1,
            'count': 1,
            'time': timezone.now().time()
        }

    def test_post_seminar_성공(self):
        client = self.client
        client.force_login(self.instructor)
        data = self.post_data
        response = client.post('/api/v1/seminar/', data=data)

        data.update({'online': True})
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data.pop('participants'), [])
        _, instructors = response.data.pop('id'), response.data.pop('instructors')
        self.assertEqual(response.data['name'], data['name'])
        self.assertEqual(response.data['capacity'], data['capacity'])
        self.assertEqual(response.data['time'], data['time'].__str__())

    def test_post_seminar_실패_403(self):
        client = self.client
        client.force_login(self.participant)
        data = self.post_data
        response = client.post('/api/v1/seminar/', data=data)

        self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)

    def test_post_seminar_실패_400(self):
        client = self.client
        client.force_login(self.instructor)
        data = self.post_data
        data.pop('capacity')
        response = client.post('/api/v1/seminar/', data=data)

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_put_seminar_성공(self):
        client = self.client
        client.force_login(self.instructor)

        seminar = self.seminar
        response = client.put(f'/api/v1/seminar/{seminar.id}/', {'name': '컴구', 'capacity': 101},
                              content_type='application/json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['name'], '컴구')
        self.assertEqual(response.data['capacity'], 101)

    def test_put_seminar_실패_400(self):
        client = self.client
        client.force_login(self.instructor)

        seminar = self.seminar
        response = client.put(f'/api/v1/seminar/{seminar.id}/', {'name': '컴구', 'capacity': -1},
                              content_type='application/json')

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_get_seminar_list(self):
        # 얘는 그냥 눈으로 확인하는 걸로...
        # 필터, 순서 등 너무 많음
        client = self.client
        client.force_login(self.participant)

        response = client.get(f'/api/v1/seminar/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_get_seminar_200(self):
        client = self.client
        client.force_login(self.participant)

        response = client.get(f'/api/v1/seminar/1/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_get_seminar_404(self):
        client = self.client
        client.force_login(self.participant)

        response = client.get(f'/api/v1/seminar/111/')
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_seminar_담당_성공(self):

        client = self.client
        client.force_login(self.both)
        data = {'role': 'instructor'}

        response = client.post('/api/v1/seminar/1/user', data=data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)


    def test_seminar_수강_성공(self):

        client = self.client
        client.force_login(self.both)
        data = {'role': 'participant'}

        response = client.post('/api/v1/seminar/1/user', data=data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        user_seminar = self.both.user_seminars.get(seminar_id=1)
        self.assertTrue(user_seminar.is_active)

    def test_seminar_드랍_드랍후재시도(self):

        client = self.client
        client.force_login(self.both)
        data = {'role': 'participant'}

        response = client.post('/api/v1/seminar/1/user', data=data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        response = client.delete('/api/v1/seminar/1/user', data=data, content_type='application/json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

        response = client.post('/api/v1/seminar/1/user', data=data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


