from django.test import TestCase, Client


# Create your tests here.
class AssignmentCheck(TestCase):

    def setUp(self):
        pass

    def test_check(self):
        response = self.client.get('/api/v1/os/')
        print(response.status_code, response.content)
