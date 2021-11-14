from rest_framework.views import Response
from rest_framework.exceptions import ValidationError, APIException

from rest_framework.exceptions import ErrorDetail
from rest_framework.views import exception_handler as ec


def exception_handler(exc, context):
    """
    Do It Yourself!
    """
    return ec(exc, context)


class SomeError(APIException):
    pass
