from django.test import TestCase


# Create your tests here.


class Test(TestCase):

    def test_context(self):
        client = self.client
        response = client.get('api/v1/survey')
