# 와플스튜디오 Backend Seminar[2] 과제

### due: 2021.09.25.(토) 23:59

### 과제 목적
- REST API의 주된 인증 방식인 JWT 방식을 이해하고, 소셜 로그인 구현을 위한 기반을 다집니다. 
- Django REST framework의 Serializers과 APIView, ViewSet을 익혀 API에 따라 적절한 사용을 하도록 합니다.
- one-to-many(many-to-one), one-to-one, many-to-many 등 다양한 table 간의 관계를 고려해 좀 더 주체적인 구현을 고민합니다.
- 여러 API에서 각종 권한 및 예외 처리를 익히고, 좀 더 복합적인 서비스 로직을 놓고 다양한 경우를 고려하는 연습을 합니다.

### 주의할 점
- 이 repo를 clone하되 로컬에 생성된 [waffle_backend](waffle_backend) 에서 바로 작업하지 마세요.
아래 '제출 방식'을 통해 생성한 본인의 `waffle-rookies-19.5-backend-2` repo를 로컬에 clone하고, 그 directory 바로 하위에 [waffle_backend](waffle_backend)
를 복붙하여 작업을 시작하세요.
- [seminar1](../seminar1) 내부의 waffle_backend와 착각하지 마세요! 또한 본인이 1번째 과제를 위해 기존에 작업했던 서버를 그대로 사용하지 마세요!
- 일반적인 경우 서로 다른 서버면 다른 Python 가상 환경을 쓰는 것이 맞고, 1번째 과제와 별도로 지금의 과제에 대해서는 별개의 Python 가상 환경을 구축하는 것이
바람직합니다. 하지만 1번째 과제와 사용하는 Python 버전, 패키지 등에 사실상 차이가 거의 없으므로, 기존 환경을 그대로 이용하셔도 괜찮습니다.
- '과제 내용'에 언급되어있는 database 설정을 반드시 참조하세요. 로컬 MySQL의 기존 `waffle_backend` database를 그대로 이용하지 않습니다.

## 과제 내용
### 1
- 정답이 있는 것은 아니지만 __과제1__ 을 구현한 결과를 [waffle_backend](waffle_backend)에 동일하게 옮겼습니다.
- [과제 2](https://github.com/wafflestudio/19.5-rookies/blob/master/django/seminar2/assignment2/README.md) 의 본격적인 시작에 앞서,
과제 1을 더 깊이 있게 살펴볼 필요가 있습니다.
- 다음주 화요일 쯤 전으로 각자 과제에 대한 피드백을 드릴 예정입니다. 구현에 정답은 없다고 생각하는 편이지만, DRF의 특징을 정확히 이해하고 사용하고 있는지 스스로 되돌아보고, 적재적소에 알맞은 기능을 사용할 수 있어야 좋을 것 같습니다.
- 이번 과제 코드를 잘 살펴보고 이해하려 노력하는 것 자체가 과제 2에 포함되며, 시작점이라고 생각해주시면 됩니다.
- __이번 과제부터 연결하는 MySQL의 database 이름을 `waffle_backend_2`으로 바꾸었습니다.__
  - 이에 따라, 자신의 로컬 MySQL에 root user 등으로 접속하여 해당 database를 새로 생성하고, `waffle-backend` user에게 `grant privileges` 하셔야 합니다.
- 이 서버 코드를 기반으로 과제 2를 진행할 예정입니다. 미리 '제출 방식'을 고려해,
이전과 마찬가지로 해당 repository를 만들고 `waffle_backend` directory를 최상위에 위치시켜두세요.
- 오늘 수업 내용 가운데 Django Template 관련 부분은 백엔드 세미나인 관계로 더 자세히 나가지 않습니다. 그러니 까먹기 전에 대충이라도 어떻게 생겼는지 한 번씩 개인적으로 확인하시고 넘어가주시면 좋을 것 같습니다.

### 2
- Model, Manager 구조도 익힐 겸, 지긋지긋한 CSRF 이슈도 지워버릴 겸, 유저 인증 방식을 JWT로 전환하고자 합니다.
- 이제 매 요청에 Authorization 헤더가 담기고, 그 값으로 유저를 식별할 수 있습니다. 더 이상 CSRF는 고려할 필요 없습니다. (DRF는 SessionMiddleware를 사용해서 CSRFViewMiddleWare보다 앞서 인증을 처리해버리기 때문인데, 자서한 사항은 [여기](https://stackoverflow.com/questions/30871033/django-rest-framework-remove-csrf) 참고 )
- 대부분 구현이 되어 있습니다. 여러분은 AbstractBaseUser, UserManager를 보시고, PyCharm 좌측 하단에 TODO 탭을 타고 들어가서 비어있는 부분을 구현해주시면 됩니다.

- 이제 유저 식별은 `username`이 아닌, `email`로 진행하려 합니다. 해당 스펙을 만족하도록 구현해주세요!
- `username`, `email (필수)`, `password` 를 request body로 signup (or) login을 하면, request body로 token이 내려보내집니다.
- 이렇게 발급된 토큰을 `Authoriaztion : JWT <token>` 형태로 헤더에 넣기만 하면, 토큰을 통해 유저 인증 관리를 할 수 있게 됩니다.
- ![image](https://user-images.githubusercontent.com/54717129/132949141-67643d74-5f29-400f-a4a3-efe6377c6ee4.png)
- 해당 내용을 구현하지 않으시면 어플이 정상 작동하지 않는다는 점 참고하여 주시기 바랍니다.

### 3
- `seminar`라는 새로운 Django app을 만들고 survey, user directory와 같은 층위에 seminar directory를 위치시키세요.
  내부에 `Seminar`라는 model을 정의하세요.
- Seminar와 User는 기본적으로 다음과 같은 관계를 가집니다. 한 User는 여러 Seminar와 관계할 수 있고, 하나의 Seminar는 여러 User를 포함할 수 있습니다.
- Django의 `ManyToManyField`를 이용하지 말고, mapping table에 해당하는 `UserSeminar`를 seminar app 내부에 직접 정의하고 `ForeignKey`를 이용해 Seminar와 User 관계를 구현하세요.
연결된 Seminar 또는 User가 삭제되는 경우에는 UserSeminar도 따라 삭제되게 합니다. 
- Seminar와 UserSeminar model 모두 생성 시점과 수정 시점을 `DateTimeField`로 저장하는 created_at(`joined_at`이 아님), updated_at column을 가집니다.
- 그 외의 column들은 아래의 과제 내용을 참고하여 자유롭게 구현해도 좋습니다. 직접 정보를 관리하고 로직을 만들기 좋은 구조를 생각해 이용하되, API의 request와 response에 대한
스펙은 정확히 지켜야 합니다. 관련 migration은 여러 번 해도 되는데, 결과적으로 완성되는 서비스 로직상 옳지 않은 데이터가 DB에 존재하지 않도록 하세요.

### 4
- 이 서비스에서 User의 유형으로는 '참여자'와 '진행자'가 있습니다. User는 진행자이면서 참여자일 수도 있습니다. 둘 다 아닌 User는 database에 존재하면 안 됩니다.
- 참여자인 User의 경우 `ParticipantProfile` model과 one-to-one 관계를 가지며, 진행자인 User의 경우 `InstructorProfile` model과
one-to-one 관계를 가집니다. 두 model을 정의해서 User와 연결시켜야 합니다.
- 두 model 모두 생성 시점과 수정 시점을 `DateTimeField`로 저장하는 created_at, updated_at column을 가집니다.
- 그 외의 column들은 아래의 과제 내용을 참고하여 자유롭게 구현해도 좋습니다. 직접 정보를 관리하고 로직을 만들기 좋은 구조를 생각해 이용하되, API의 request와 response에 대한
스펙은 정확히 지켜야 합니다. 관련 migration은 여러 번 해도 되는데, 결과적으로 완성되는 서비스 로직상 옳지 않은 데이터가 DB에 존재하지 않도록 하세요.

### 5
- 회원가입 API인 `POST /api/v1/signup/`는 이미 정의되어 있습니다. request의 body에 `role`의 값이 `participant`인지,
`instructor`인지에 따라 ParticipantProfile 또는 InstructorProfile이 User와 함께 생성되도록 하세요. `role`이 둘 중 하나의 값이 아닌 경우 `400`으로 적절한 에러 메시지와 함께 응답합니다.
- 회원가입 API에 대한 아래의 내용은 [#186 issue](https://github.com/wafflestudio/rookies/issues/186)에 따라 명시되었습니다. 기본적으로 5.의 관련 내용과 같다고 생각하시면 됩니다.
  - `role`이 `participant`인 User는 소속 대학에 대한 정보(university)를 저장할 수 있습니다. 정보가 없는 경우, `""`으로 DB에 저장됩니다.
  - `role`이 `participant`인 User는 Seminar에 참여할 수 있는지에 대한 정보(accepted)를 받을 수 있습니다. 정보가 없는 경우, `True`로 받아들이면 됩니다.(사후 수정 - [#218 issue](https://github.com/wafflestudio/rookies/issues/218) 참고)
  - `role`이 `instructor`인 User는 소속 회사에 대한 정보(company)와 자신이 몇 년차 경력인지(year)를 저장할 수 있습니다. 정보가 없는 경우, 각각 `""`과 null으로 DB에 저장됩니다.
  - 참여자인 User가 request body에 company를 포함하는 등, 자신의 유형과 맞지 않는 정보가 들어오면 그냥 무시하면 됩니다. body가 완전히 비어있어도 무시하면 됩니다.
  - year에 0 또는 양의 정수가 아닌 값이 오는 경우는 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다.
-  `PUT /api/v1/user/me/`, `GET /api/v1/user/{user_id}/`의 response body는 `UserSerializer`를 그대로 이용하되,
아래와 같이 확장 하세요. 이번 과제에서 정의하는 model의 column 이름은 자유롭게 정해도 되지만, response는 하나의 차이도 없이 같아야 합니다.
값의 위치에 `Participant university` 등으로 적혀있는 것은, 어떤 model에 해당 정보를 어떤 type으로 저장하면 될지 힌트를 제공하는 것이고, 실제 저장은 다른 구조로 다른 이름으로 해도 됩니다.
단, 2.와 3.에서 직접 언급한 table 및 column의 구조와 이름은 지키셔야 합니다.
null이 가능하다고 명시하지 않은 값엔 null이 들어가면 안 됩니다. 처음부터 이와 같은 body를 갖출 수는 없고,
아래에 기술되는 과제 내용을 참고하여 개발이 진행되어감에 따라 이에 가까워질 것입니다.
````
{
    "id": User id,
    "username": User username,
    "email": User email,
    "first_name": User first_name,
    "last_name": User last_name,
    "last_login": User last_login,
    "date_joined": User date_joined,
    "participant": {
        "id": ParticipantProfile id,
        "university": ParticipantProfile university(string),
        "accepted": ParticipantProfile accepted(bool),
        "seminars": [
            {
                "id": Seminar id,
                "name": Seminar name(string),
                "joined_at": UserSeminar joined_at(datetime),
                "is_active": UserSeminar is_active(bool),
                "dropped_at": UserSeminar dropped_at(datetime, 정보가 없는 경우 null)
            },
            ...
        ] (참여하는 Seminar가 없는 경우 [], is_active가 false인 Seminar도 포함)
    } (ParticipantProfile이 없는 경우 null),
    "instructor": {
        "id": InstructorProfile id,
        "company": InstructorProfile company(string),
        "year": InstructorProfile year(number, 정보가 없는 경우 null),
        "charge": {
            "id": Seminar id,
            "name": Seminar name(string),
            "joined_at": UserSeminar joined_at(datetime)
        } (담당하는 Seminar가 없는 경우 null)
    } (InstructorProfile이 없는 경우 null)
}
````

### 6
- 기존에 존재하는 API `PUT /api/v1/user/me/`를 확장해야 합니다.
- 참여자인 User는 소속 대학에 대한 정보(university)를 수정할 수 있습니다. 정보가 없는 경우, `""`으로 DB에 저장됩니다.
- 진행자인 User는 소속 회사에 대한 정보(company)와 자신이 몇 년차 경력인지(year)를 수정할 수 있습니다. 정보가 없는 경우, 각각 `""`과 null으로 DB에 저장됩니다.
- 위의 정보와 함께, 기존에 `PUT /api/v1/user/me/` 수정 가능하던 모든 정보 또는 일부를 마찬가지로 수정할 수 있어야 합니다.
- 참여자 또는 진행자인 User는 Participant의 accepted를 제외하고는 해당하는 정보들을 모두 수정할 수 있습니다. 당연히 created_at, updated_at은 request로 받아 수정하면 안됩니다.
- 참여자인 User가 request body에 company를 포함하는 등, 자신의 유형과 맞지 않는 정보가 들어오면 그냥 무시하면 됩니다. body가 완전히 비어있어도 무시하면 됩니다.
- university와 company에 0글자가 오는 경우에는 ""으로 수정하면 됩니다.
- year에 0 또는 양의 정수가 아닌 값이 오는 경우는 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다.

### 7
- User는 진행자로 가입한 경우, 사후적으로 참여자로 등록할 수 있습니다. `POST /api/v1/user/participant/`를 통해 가능하며, 이때 `university`를 함께
request body로 제공할 수 있습니다. 이것이 포함되는 경우, 서버는 이를 저장해야합니다. university의 입력이 없는 경우 `""`으로 저장하면 됩니다. accepted의 입력이 없는 경우, `True`로 받아들이면 됩니다.
이미 참여자인 사람이 또 요청하면 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다. 성공적인 경우 `201`로 응답하고 4.의 response body와 같은 구조를 가지면 됩니다.
- 한 번 진행자 또는 참여자가 된 User는 서비스 로직상 해당 Profile을 잃지 않습니다.

### 8
- `POST /api/v1/seminar/`를 seminar app 내에 구현하세요. 관련 `serializers.py`와 `urls.py`를 해당 app 내에 생성하여 관련 코드를 작성하세요.
- request body는 세미나 이름(name), 정원(capacity), 세미나 횟수(count), 정기 세미나 시간(time), 온라인 여부(online)를 포함해야 합니다.
online 여부 외에는 하나라도 빠지면 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다. name에 0글자가 오는 경우, capacity와 count에
양의 정수가 아닌 값이 오는 경우, time이 `14:30`과 같은 형태가 아닌 경우는 `400`으로 응답합니다. time은 24시간제로 받아들이며, `TimeField`를
이용해 저장하세요. online은 True 또는 False와 같은 대소문자 무관한 문자열([#183 issue](https://github.com/wafflestudio/rookies/issues/183) 참고)이어야 하며, 아닌 경우 `400`으로 응답합니다. online이 request body에 포함되지
않는 경우, `True`를 기본으로 합니다. response는 아래와 같아야 하며, `201`로 응답합니다. model의 column 이름은 자유롭게 정해도 되지만, response는 하나의 차이도 없이 같아야 합니다.
세미나 진행자 자격을 가진 User만 요청할 수 있으며, 그렇지 않은 경우 `403`으로 응답합니다. 요청한 User가 기본적으로 해당 Seminar의 담당자가 됩니다.
````
{
    "id": Seminar id,
    "name": Seminar name,
    "capacity": Seminar capacity,
    "count": Seminar count,
    "time": Seminar time,
    "online": Seminar online,
    "instructors": [
        {
            "id": User id,
            "username": User username,
            "email": User email,
            "first_name": User first_name,
            "last_name": User last_name,
            "joined_at": UserSeminar joined_at(datetime)
        },
        ...
    ]
    "participants": [
         {
            "id": User id,
            "username": User username,
            "email": User email,
            "first_name": User first_name,
            "last_name": User last_name,
            "joined_at": UserSeminar joined_at(datetime)
            "is_active": UserSeminar is_active(bool),
            "dropped_at": UserSeminar dropped_at(datetime, 정보가 없는 경우 null)
         },
         ...
    ] (참여하는 Participant가 없는 경우 [], is_active가 false인 Participant도 포함)
}
````
- `PUT /api/v1/seminar/{seminar_id}/`로 Seminar의 정보 일부를 수정할 수 있습니다. body가 완전히 비어있어도 무시하면 됩니다. 성공적인 경우 세미나
생성 API와 동일한 구조의 body와 함께 `200`으로 응답합니다. 단, 현재 해당 Seminar에 참여 중인 참여자 수(is_active가 false인 Participant는 참여 중인 것이 아니므로 제외)보다 적은 값으로 capacity를 수정하려고 하면 `400`으로 응답하며
적절한 에러 메시지를 포함합니다. `seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다. 이 동작은 해당 Seminar의 담당자들만이 요청할 수 있으며,
세미나 진행자(Instructor)더라도 담당이 아니면 참여자에게와 마찬가지로 `403`으로 적절한 에러 메시지와 함께 응답합니다.

- `GET /api/v1/seminar/{seminar_id}/`로 Seminar의 정보를 가져올 수 있습니다. `seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다.
세미나 생성 API와 동일한 구조의 body와 함께 `200`으로 응답합니다.

- `GET /api/v1/seminar/`로 여러 Seminar의 정보를 가져올 수 있으며, status code는 `200`입니다. 이 때, 선택적으로 request에 query params를 포함할 수 있습니다.
  - `GET /api/v1/seminar/?name={name}`으로 query param이 주어지면, {name} str을 포함하는 name을 가진 Seminar들을 모두 가져옵니다.
해당하는 Seminar가 없으면 `[]`를 body로 합니다.
  - response는 아래와 같으며, 기본적으로 Seminar의 created_at을 기준으로 가장 최근에 만들어진 Seminar가 위에 오도록 body를 구성합니다.
    ````
    [
        {
            "id": Seminar id,
            "name": Seminar name,
            "instructors": [
                {
                    "id": User id,
                    "username": User username,
                    "email": User email,
                    "first_name": User first_name,
                    "last_name": User last_name,
                    "joined_at": UserSeminar joined_at(datetime)
                },
                ...
            ],
            "participant_count": Seminar에 Participant로 참여 중인 User의 수 (is_active가 false인 Participant는 참여 중인 것이 아니므로 제외)
        }
    ]
    ````
  - `GET /api/v1/seminar/?order=earliest`으로 query param이 주어지면, Seminar의 created_at을 기준으로 가장 오래된 Seminar가 위에
오도록 body를 구성합니다. order에 `earliest`가 아닌 값들이 오는 경우는 무시하고 기본적인 최신 순으로 정렬하면 됩니다.
그 외 name, order가 아닌 query param key가 포함되는 경우도 무시하면 됩니다. name, order는 함께 적용할 수 있으며, 두 param 모두 없으면
전체 Seminar를 최신 순으로 정렬하면 됩니다.

### 9
- 진행자(Instructor)인 User는 Seminar 담당을 맡을 수 있고, 참여자(Participant)인 User는 Seminar에 참여할 수 있습니다. 두 동작은 모두 `POST /api/v1/seminar/{seminar_id}/user/`를 통해
이뤄집니다. `seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다. request body에 `role`의 값이 `participant` 또는 `instructor`인지에 따라 어떤 자격으로 해당 세미나에 참여하는지 결정되며,
`seminar_id`을 통해 어떤 Seminar에 참여할지 결정됩니다.
- `role`이 둘 중 하나의 값이 아닌 경우 `400`으로 적절한 에러 메시지와 함께 응답하며,
해당하는 자격을 가지지 못한(ParticipantProfile이 없는 User가 `participant`로 요청하는 등) User가 요청하는 경우 `403`으로 적절한 에러 메시지와 함께 응답합니다.
- Participant 자격을 갖고 있다고 하더라도, accepted가 False인 User가 세미나에 참여하는 요청을 하는 경우 `403`으로 적절한 에러 메시지와 함께 응답합니다.
- `participant`로 Seminar에 참여할 때, Seminar의 capacity가 해당 Seminar에 참여자로서 있는 User들로 인해 이미 가득 찬 경우 `400`으로 응답하며 적절한 에러 메시지를 포함합니다.
- Instructor 자격을 갖고 있다고 하더라도, 자신이 이미 담당하고 있는 세미나가 있는 경우 `400`으로 적절한 에러 메시지와 함께 응답합니다.
- 이미 어떤 자격으로든 해당 세미나에 참여하고 있다면 `400`으로 적절한 에러 메시지와 함께 응답합니다.
- 성공적으로 처리된 경우, 해당 세미나에 대해 세미나 생성 API와 동일한 구조의 body와 함께 `201`로 응답합니다.
- 세미나 참여자가 세미나에 참여한 후 관련 API response들에서 해당 정보의 is_active는 기본적으로 True입니다. joined_at은 세미나 진행자 또는 세미나 참여자가
해당 세미나를 시작한 시점을 나타냅니다.

### 10
- `DELETE /api/v1/seminar/{seminar_id}/user/`를 통해 세미나 참여자는 해당 세미나를 중도 포기할 수 있습니다.
`seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다. 세미나 진행자가 해당 요청을 하면 `403`으로 적절한 에러 메시지와 함께 응답합니다.
- 정상적으로 처리되는 경우, 해당 User와 Seminar의 관계에서 is_active를 False로 설정하고, 그 시점을 dropped_at으로 저장하며, 세미나 생성 API와
동일한 구조의 body와 함께 `200`으로 응답합니다.
중도 포기한 세미나는 `POST /api/v1/seminar/{seminar_id}/user/`를 이용해 다시 참여할 수 없습니다. 이 경우 `400`으로 적절한 에러 메시지와 함께 응답합니다.
- 해당 세미나에 참여하고 있지 않은 경우, 무시하고 body와 함께 `200`으로 응답하면 됩니다.

### 11
- `waffle-rookies-19.5-backend-2`의 `README.md`에 과제 관련 하고 싶은 말, 어려웠던 점 등을 남겨주세요. 물론 적극적으로 해결되어야 할 피드백이나
질문 사항은 [Issues](https://github.com/wafflestudio/rookies/issues) 등을 이용해주세요!
- `GET /api/v1/seminar/` API에 관련해, Django Debug Toolbar를 이용하여 query를 보고 스크린샷과 함께 느낀 점이나 이를 통해 조금이라도 query를 개선한 부분을 남겨주세요.
물론 다른 API들에 대해서 추가적으로 포함하셔도 좋습니다.
- 개발 과정의 흐름이나 시행 착오를 알아보기 좋게 작성해주셔도 좋습니다.
- 구현을 하다가 과제 내용에 명시되지 않은 경우가 있다고 생각되면 [Issues](https://github.com/wafflestudio/rookies/issues) 에서 질문해주세요.

## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-backend-2`라는 이름으로 private repository를 개설합니다.

![스크린샷 2020-08-30 02 12 24](https://user-images.githubusercontent.com/35535636/91642533-097dec80-ea67-11ea-96e4-ab0dfa757187.png)

2. 개설 후 Settings > Manage access 로 들어갑니다.

![스크린샷 2020-08-30 02 13 52](https://user-images.githubusercontent.com/35535636/91642567-5eb9fe00-ea67-11ea-9382-89fcce03be70.png)

3. collaborator로, 세미나 운영진들을 초대합니다.

![스크린샷 2020-08-30 02 14 59](https://user-images.githubusercontent.com/35535636/91642588-87da8e80-ea67-11ea-9d5a-60a3596463c9.png)

- [@Jhvictor4](https://github.com/Jhvictor4) 
- [@gina0605](https://github.com/gina0605)
- [@dodo4114](https://github.com/dodo4114)
- [@PFCjeong](https://github.com/PFCJeong)

![스크린샷 2020-08-30 02 16 17](https://user-images.githubusercontent.com/35535636/91642619-cbcd9380-ea67-11ea-84ea-1a0729103755.png)

4. 아래 스크린샷과 같은 directory 구조를 갖추어야 합니다.

```
/README.md
/.gitignore
/waffle_backend/manage.py
/waffle_backend/waffle_backend/*
/waffle_backend/survey/*
/waffle_backend/user/*
...
```

![스크린샷 2020-08-30 03 16 21](https://user-images.githubusercontent.com/35535636/91643553-3b934c80-ea6f-11ea-8e5c-c20b1e6e42a3.png)

![스크린샷 2020-08-30 03 16 29](https://user-images.githubusercontent.com/35535636/91643554-3cc47980-ea6f-11ea-9ade-087b4845df11.png)

5. 가급적 repository 생성과 collaborator 지정은 미리 해둬주세요! 제출 방식을 다들 올바로 이해하고 계신지와 함께, 가능하다면 대략적인 진행상황을 보면서 필요하면 몇 가지 말씀을 더 드릴 수도 있습니다.


6. 과제 진행은 다음 절차를 따라주세요

  - 현재 디렉토리에 있는 [waffle_backend](waffle_backend) 를 clone 후 waffle-rookies-19.5-backend-2 에 복사합니다.
  - **waffle-rookies-19.5-backend-2 디렉토리에서 `git checkout -b workspace` 로 이번 과제를 진행할 새로운 브랜치를 만들고 이동합니다**<br>Git Desktop과 같은 GUI 툴을 사용하신다면 workspace라는 이름으로 New branch를 생성해주세요.
  - 해당 branch에서 작업을 완료해주세요. (**master branch에 push하면 안됩니다. git push origin workspace로 workspace branch에만 변경사항을 업로드해주세요.**)
  - 과제를 마치셨으면 마지막으로 workspace branch에 push 해주시고 Pull Request를 날려주시면 됩니다. (master <- workspace)
  - 만약 master에 변경사항을 업로드한 경우 workspace branch에서 `git merge master`를 통해 master의 변경사항을 workspace branch로 가져오고 `git checkout master`를 이용해 master branch로 이동 <br>
    ```
    git revert --no-commit HEAD~1..
    git commit
    git push origin master
    ```
    를 이용해 commit을 돌리시면 됩니다. (HEAD~ 뒤의 숫자는 되돌릴 commit의 수)
  - git이 어려운 경우 [OT자료](../../wafflestudio%2018.5%20rookies%20OT.pdf), https://backlog.com/git-tutorial/kr/stepup/stepup1_1.html 등을 참고해주세요.

7. 제출 기한 전까지 PR을 완료하고 API CALL을 해주시면, 지정된 세미나 운영진들이 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.

8. master branch의 상태는 반드시 [skeleton code](waffle_backend)와 동일해야합니다. 

## 참고하면 좋은 것들 !!! 읽어보세요
- 추후 점진적으로 추가 예정입니다.
- https://www.django-rest-framework.org/api-guide/requests/#query_params
- https://www.django-rest-framework.org/api-guide/permissions/#custom-permissions
- https://www.django-rest-framework.org/api-guide/generic-views/#generic-views
- https://www.django-rest-framework.org/api-guide/generic-views/#get_serializer_classself

- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.
- [Issues](https://github.com/wafflestudio/rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다. 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요.
또한 Issue 제목에 과제 내용의 번호 등을 사용하시기보다, 궁금한 내용의 키워드가 포함되도록 해주세요. 답이 정해져있지 않은 설계에 대한 고민 공유도 좋습니다.
- 문제를 해결하기 위해 질문하는 경우라면, 질문을 통해 기대하는 바, (가급적 스크린샷 등을 포함한) 실제 문제 상황, 이를 해결하기 위해 시도해본 것, 예상해본 원인 등을 포함시켜 주시는 것이 자신과 질문을 답변하는 사람, 제3자 모두에게 좋습니다.
- 저는 직장을 다니고 있으므로 아주 빠른 답변은 어려울 수 있고, 특히 과제 마감 직전에 여러 질문이 올라오거나 하면 마감 전에 모든 답변을 드릴 수 있다는 것은
보장하기 어렵다는 점 이해해주시면 좋겠습니다. 그리고 세미나 진행자들이 아니어도, 참여자 분들 모두가 자신이 아는 선에서 서로 답변을 하고 도우시려고 하면 아주 좋을 것 같습니다.
- 과제의 핵심적인 스펙은 바뀌지 않을 것이며 설령 있다면 공지를 따로 드릴 것입니다. 하지만 문구나 오타 수정 등의 변경은 수시로 있을 수 있고,
특히 '참고하면 좋을 것들'에는 추가 자료들을 첨부할 수도 있습니다. 때문에 종종 이 repository를 pull 받아주시거나, 이 페이지를 GitHub에서 종종 다시 확인해주시기 바랍니다.
