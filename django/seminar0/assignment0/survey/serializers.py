from survey.models import OperatingSystem, SurveyResult


def serialize_survey_result(survey: SurveyResult):
    return {
        'id': survey.id,
        'os': serialize_os(survey.os),
        'python': survey.python,
        'rdb': survey.rdb,
        'programming': survey.programming,
        'major': survey.major,
        'grade': survey.grade,
        'backend_reason': survey.backend_reason,
        'waffle_reason': survey.waffle_reason,
        'say_something': survey.say_something,
        'timestamp': survey.timestamp,
    }


def serialize_os(os: OperatingSystem):
    return {
        'id': os.id,
        'name': os.name,
        'description': os.description,
        'price': os.price,
    }
