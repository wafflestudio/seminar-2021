# 와플스튜디오 Spring Boot Seminar[2] 과제

## 제출기한 2021/9/27 23:59

### 과제 목적
- one-to-many(many-to-one), one-to-one, many-to-many 등 다양한 table 간의 관계를 고려해 좀 더 주체적인 구현을 고민합니다.
- 여러 API에서 각종 권한 및 예외 처리를 익히고, 좀 더 복합적인 서비스 로직을 놓고 다양한 경우를 고려하는 연습을 합니다.

### 주의할 점
- assignment2-skeleton 브랜치를 만들어 스켈레톤을 저장한 후 master(main)으로 머지, conflict 해결 프로세스를 거쳐 merge시키면 됩니다. 이를 통해 스켈레톤 코드 업데이트를 쉽게 하고 마스터 브랜치를 훼손하는 실수를 방지할 수 있습니다.
- conflict 해결 시 스켈레톤 코드를 그대로 덮어쓰셔도 됩니다. 만약 assignment1에서 구현한 사항을 포함하고 싶은 경우 적절히 conflict 해결을 해주시기 바랍니다.
- master(main)브랜치에는 assignment1 + 과제2 스켈레톤 코드까지 머지된 상태로 유지되어야 합니다.
- assignment2 브랜치는 master에 assignment1, 과제2 스켈레톤 코드가 머지된 상태에서 분기시켜 주시기 바랍니다.
- 2021/9/16 00:20 기준 스켈레톤 코드가 업데이트 되었습니다. 이전에 스켈레톤 코드를 받으신 분은 새로 머지시켜 주시기 바랍니다.

## 과제 내용

### 세미나장의 추가 구현

- global.config에 SecurityConfig을 추가했습니다. 이를 통해 인증받지 않은 유저로부터의 api로의 접근을 막습니다. 현재 로그인하지 않은 유저는 로그인에 해당하는 `POST /api/v1/users/signin/`과 회원가입에 해당하는 `POST /api/v1/users/` 두 가지 api만 요청할 수 있습니다. 나머지 요청은 AuthenticationFilter를 통해 걸러집니다.
- global.auth 패키지에 SigninAuthenticationFilter와 JwtAuthenticationFilter를 추가했습니다.
  - SigninAuthenticationFilter는`POST /api/v1/users/signin/`요청이 왔을 때 로그인을 처리해주는 필터입니다. 해당 api만 처리하며 이메일, 비밀번호를 다음과 같이 body에 실어보낼 경우 로그인이 정상적으로 처리됩니다.
    ```
    {
        "email": "hankchoi@~~~.com",
        "password": "wafflestudio"
    }
    ```
  - 로그인 혹은 회원가입 시에 Response 헤더에 Authentication이라는 key로 JWT가 반환됩니다. 다른 요청을 보낼 때 해당 JWT를 Authentication 헤더에 실어보낼 경우 서버에 현재 로그인한 유저의 정보를 전달할 수 있습니다.
  - JwtAuthenticationFilter는 각 request마다 JWT를 분석해 유저가 해당 api에 대한 접근 권한이 있는지를 파악합니다.
  - 접근 권한을 세세하게 조정하고 싶다면 SecurityConfig에서 각 api별로 권한을 설정하면 됩니다. 유저의 권한은 유저 테이블에 roles로 저장되어 있습니다. 여러 role을 가지는 경우 roles 값을 "admin, a, b, c" 와 같이 저장하면 됩니다. 아래 과제스펙에 나오는 role과는 다르고 서버의 접근권한을 나타냅니다. 물론 동기화 시킬 수도 있습니다만 하고 싶으신 분만 하시면 됩니다!. 
  - `@CurrentUser`라는 어노테이션을 만들었습니다. 다음과 같이 controller 함수를 작성하는 경우 자동으로 user 인자에 로그인한 유저의 정보가 전달됩니다. 제가 만든 `/api/v1/users/me/`확인하시면 이해가 빠를 것 같습니다.
    ```Kotlin
    fun me(@CurrentUser user:User)
    ```
   
### 1
- 과제 2 의 본격적인 시작에 앞서, 해당 코드 1을 더 깊이 있게 살펴볼 필요가 있습니다.
- 이 코드를 잘 살펴보고 이해하려 노력하는 것 자체가 과제 2에 포함되며, 시작점이라고 생각해주시면 됩니다.
- 로드 부담을 줄이기 위해 유저 회원가입, 로그인, Exception handler를 구현해놨습니다.
- Exception handler부분과 Spring security, jwt token을 이용한 로그인 구현을 꼼꼼히 확인 부탁드립니다.
- 이 코드 또한 약간의 수정이 일어날 수 있습니다.

### 2
- 이 서비스에서 User의 유형으로는 '참여자'와 '진행자'가 있습니다. User는 진행자이면서 참여자일 수도 있습니다. 둘 다 아닌 User는 database에 존재하면 안 됩니다.
- 참여자인 User의 경우 `ParticipantProfile` model과 one-to-one 관계를 가지며, 진행자인 User의 경우 `InstructorProfile` model과
one-to-one 관계를 가집니다. 두 model을 정의해서 User와 연결시켜야 합니다.
- 두 model 모두 생성 시점과 수정 시점을 `DateTimeField`로 저장하는 created_at, updated_at column을 가집니다.

### 3
- `seminar`라는 새로운 도메인을 추가할 것입니다.
- 세미나에는 여러명의 '진행자'와 여러명의 '참여자'가 존재할 수 있습니다.
- 우선 '참여자'만 고려할 때 세미나와 '참여자'간에는 ManyToMany관계가 성립합니다. '참여자'는 여러 세미나를 들을 수 있고 또 한 세미나에는 여러 '참여자'가 존재합니다.
- `@ManyToMany` annotation을 이용하지 말고, mapping table에 해당하는 `SeminarParticipant`를 직접 정의하고 `ForeignKey`를 이용해 Seminar와 ParticipantProfile 의 관계를 구현하세요.
연결된 Seminar 또는 User가 삭제되는 경우에는 SeminarParticipant도 따라 삭제되게 합니다.
- '진행자'의 경우 한 세미나만 맡을 수 있지만 한 세미나에는 여러 '진행자'가 있을 수 있습니다.
- 세미나는 유저의 Profile과 관계를 설정하는 것이 다루기 더 쉬울 것입니다.
- Seminar와 SeminarParticipant model 모두 생성 시점과 수정 시점을 `DateTimeField`로 저장하는 created_at(`joined_at`이 아님), updated_at column을 가집니다. (BaseTimeEntity 참고)
- 그 외의 column들은 아래의 과제 내용을 참고하여 자유롭게 구현해도 좋습니다. 직접 정보를 관리하고 로직을 만들기 좋은 구조를 생각해 이용하되, API의 request와 response에 대한 스펙은 아래와 동일해야 합니다.

### 4
- 회원가입 API인 `POST /api/v1/users/`는 이미 정의되어 있습니다. request의 body에 `role`의 값이 `participant`인지,
`instructor`인지에 따라 ParticipantProfile 또는 InstructorProfile 모델이 User와 함께 생성되도록 하세요. `role`이 둘 중 하나의 값이 아닌 경우 `400`으로 적절한 에러 메시지와 함께 응답합니다.
  - `role`이 `participant`인 User는 소속 대학에 대한 정보(university)를 저장할 수 있습니다. 정보가 없는 경우, `""`으로 DB에 저장됩니다.
  - `role`이 `participant`인 User는 Seminar에 참여할 수 있는지에 대한 정보(accepted)를 받을 수 있습니다. 정보가 없는 경우, `True`로 받아들이면 됩니다.
  - `role`이 `instructor`인 User는 소속 회사에 대한 정보(company)와 자신이 몇 년차 경력인지(year)를 저장할 수 있습니다. 정보가 없는 경우, 각각 `""`과 null으로 DB에 저장됩니다.
  - 참여자인 User가 request body에 company를 포함하는 등, 자신의 유형과 맞지 않는 정보가 들어오면 그냥 무시하면 됩니다. body가 완전히 비어있어도 무시하면 됩니다.
  - year에 0 또는 양의 정수가 아닌 값이 오는 경우는 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다.
- `GET /api/v1/users/{user_id}/`를 만들어 주세요. 요청은 우선 `/api/v1/users/me/`와 같습니다.
- `POST /api/v1/users/`, `POST /api/v1/users/login/`, `GET /api/v1/users/me/`, `GET /api/v1/user/{user_id}/`의 response body는 `UserDto.Response`를 그대로 이용하되,
아래와 같이 확장 하세요. 이번 과제에서 정의하는 model의 column 이름은 자유롭게 정해도 되지만, response는 하나의 차이도 없이 같아야 합니다.
값의 위치에 `Participant university` 등으로 적혀있는 것은, 어떤 model에 해당 정보를 어떤 type으로 저장하면 될지 힌트를 제공하는 것이고, 실제 저장은 다른 구조로 다른 이름으로 해도 됩니다.
단, 2.와 3.에서 직접 언급한 table 및 column의 구조와 이름은 지키셔야 합니다.
null이 가능하다고 명시하지 않은 값엔 null이 들어가면 안 됩니다. 처음부터 이와 같은 body를 갖출 수는 없고,
아래에 기술되는 과제 내용을 참고하여 개발이 진행되어감에 따라 이에 가까워질 것입니다.
````
{
    "id": User id,
    "name": User name,
    "email": User email,
    "date_joined": User date_joined,
    "participant_profile": {
        "id": ParticipantProfile id,
        "university": ParticipantProfile university(string),
        "accepted": ParticipantProfile accepted(bool),
        "seminars": [
            {
                "id": Seminar id,
                "name": Seminar name(string),
                "joined_at": SeminarParticipant joined_at(datetime),
                "is_active": SeminarParticipant is_active(bool),
                "dropped_at": SeminarParticipant dropped_at(datetime, 정보가 없는 경우 null)
            },
            ...
        ] (참여하는 Seminar가 없는 경우 [], is_active가 false인 Seminar도 포함)
    } (ParticipantProfile이 없는 경우 null),
    "instructor_profile": {
        "id": InstructorProfile id,
        "company": InstructorProfile company(string),
        "year": InstructorProfile year(number, 정보가 없는 경우 null),
        "charge": {
            "id": Seminar id,
            "name": Seminar name(string),
        } (담당하는 Seminar가 없는 경우 null)
    } (InstructorProfile이 없는 경우 null)
}
````

### 5
- `PUT /api/v1/user/me/`를 만들어야 합니다. 해당 api는 자신의 정보를 수정하는 api입니다.
- 참여자인 User는 소속 대학에 대한 정보(university)를 수정할 수 있습니다. 정보가 없는 경우, `""`으로 DB에 저장됩니다.
- 진행자인 User는 소속 회사에 대한 정보(company)와 자신이 몇 년차 경력인지(year)를 수정할 수 있습니다. 정보가 없는 경우, 각각 `""`과 null으로 DB에 저장됩니다.
- 참여자 또는 진행자인 User는 Participant의 accepted를 제외하고는 해당하는 정보들을 모두 수정할 수 있습니다. 당연히 created_at, updated_at은 request로 받아 수정하면 안됩니다.
- 참여자인 User가 request body에 company를 포함하는 등, 자신의 유형과 맞지 않는 정보가 들어오면 그냥 무시하면 됩니다. body가 완전히 비어있어도 무시하면 됩니다.
- university와 company에 0글자가 오는 경우에는 ""으로 수정하면 됩니다.
- year에 0 또는 양의 정수가 아닌 값이 오는 경우는 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다.

### 6
- User는 진행자로 가입한 경우, 사후적으로 참여자로 등록할 수 있습니다. `POST /api/v1/user/participant/`를 통해 가능하며, 이때 `university`를 함께
request body로 제공할 수 있습니다. 이것이 포함되는 경우, 서버는 이를 저장해야합니다. university의 입력이 없는 경우 `""`으로 저장하면 됩니다. accepted의 입력이 없는 경우, `True`로 받아들이면 됩니다.
이미 참여자인 사람이 또 요청하면 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다. 성공적인 경우 `201`로 응답하고 4.의 response body와 같은 구조를 가지면 됩니다.
- 한 번 진행자 또는 참여자가 된 User는 서비스 로직상 해당 Profile을 잃지 않습니다.

### 7.
- `POST /api/v1/seminars/`를 seminar app 내에 구현하세요.
- request body는 세미나 이름(name), 정원(capacity), 세미나 횟수(count), 정기 세미나 시간(time), 온라인 여부(online)를 포함해야 합니다.
online 여부 외에는 하나라도 빠지면 `400`으로 응답하며, 적절한 에러 메시지를 포함합니다. name에 0글자가 오는 경우, capacity와 count에
양의 정수가 아닌 값이 오는 경우, time이 `14:30`과 같은 형태가 아닌 경우는 `400`으로 응답합니다. time은 24시간제로 받아들이면 됩니다.  online은 True 또는 False와 같은 대소문자 무관한 문자열이어야 하며, 아닌 경우 `400`으로 응답합니다. online이 request body에 포함되지
않는 경우, `True`를 기본으로 합니다. response는 아래와 같아야 하며, `201`로 응답합니다. model의 column 이름은 자유롭게 정해도 되지만, response는 하나의 차이도 없이 같아야 합니다.
세미나 진행자 자격을 가진 User만 요청할 수 있으며, 그렇지 않은 경우 `403`으로 응답합니다. 요청한 User가 기본적으로 해당 Seminar의 담당자가 됩니다.
````json
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
            "name": User name,
            "email": User email,
            "company": InstructorProfile company
        },
        ...
    ]
    "participants": [
         {
            "id": User id,
            "name": User name,
            "email": User email,
            "university": ParticipantProfile university,
            "joined_at": SeminarParticipant joined_at(datetime),
            "is_active": SeminarParticipant is_active(bool),
            "dropped_at": SeminarParticipant dropped_at(datetime, 정보가 없는 경우 null)
         },
         ...
    ] (참여하는 Participant가 없는 경우 [], is_active가 false인 Participant도 포함)
}
````
- `PUT /api/v1/seminars/{seminar_id}/`로 Seminar의 정보 일부를 수정할 수 있습니다. body가 완전히 비어있어도 무시하면 됩니다. 성공적인 경우 세미나
생성 API와 동일한 구조의 body와 함께 `200`으로 응답합니다. 단, 현재 해당 Seminar에 참여 중인 참여자 수(is_active가 false인 Participant는 참여 중인 것이 아니므로 제외)보다 적은 값으로 capacity를 수정하려고 하면 `400`으로 응답하며
적절한 에러 메시지를 포함합니다. `seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다. 이 동작은 해당 Seminar의 담당자들만이 요청할 수 있으며,
세미나 진행자(Instructor)더라도 담당이 아니면 참여자에게와 마찬가지로 `403`으로 적절한 에러 메시지와 함께 응답합니다.

- `GET /api/v1/seminar/{seminar_id}/`로 Seminar의 정보를 가져올 수 있습니다. `seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다.
세미나 생성 API와 동일한 구조의 body와 함께 `200`으로 응답합니다.

- `GET /api/v1/seminar/`로 여러 Seminar의 정보를 가져올 수 있으며, status code는 `200`입니다. 이 때, 선택적으로 request에 query params를 포함할 수 있습니다.
  - `GET /api/v1/seminar/?name={name}`으로 query param이 주어지면, {name} str을 포함하는 name을 가진 Seminar들을 모두 가져옵니다.
해당하는 Seminar가 없으면 `{"count":0,"results":[]}`를 body로 합니다.
  - response는 아래와 같으며, 기본적으로 Seminar의 created_at을 기준으로 가장 최근에 만들어진 Seminar가 위에 오도록 body를 구성합니다.
    ````json
    {
        "count": results count,
        "results":[
            {
                "id": Seminar id,
                "name": Seminar name,
                "instructors": [
                    {
                        "id": User id,
                        "name": User name,
                        "email": User email,
                        "company": InstructorProfile company
                    },
                    ...
                ],
                "participant_count": Seminar에 Participant로 참여 중인 User의 수 (is_active가 false인 Participant는 참여 중인 것이 아니므로 제외)
            }
        ]
    }
    ````
  - `GET /api/v1/seminars/?order=earliest`으로 query param이 주어지면, Seminar의 created_at을 기준으로 가장 오래된 Seminar가 위에
오도록 body를 구성합니다. order에 `earliest`가 아닌 값들이 오는 경우는 무시하고 기본적인 최신 순으로 정렬하면 됩니다.
그 외 name, order가 아닌 query param key가 포함되는 경우도 무시하면 됩니다. name, order는 함께 적용할 수 있으며, 두 param 모두 없으면
전체 Seminar를 최신 순으로 정렬하면 됩니다.

### 8
- 진행자(Instructor)인 User는 Seminar 담당을 맡을 수 있고, 참여자(Participant)인 User는 Seminar에 참여할 수 있습니다. 두 동작은 모두 `POST /api/v1/seminars/{seminar_id}/user/`를 통해
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

### 9
- `DELETE /api/v1/seminars/{seminar_id}/user/me/`를 통해 세미나 참여자는 해당 세미나를 중도 포기할 수 있습니다.
`seminar_id`에 해당하는 Seminar가 없는 경우 `404`로 응답합니다. 세미나 진행자가 해당 요청을 하면 `403`으로 적절한 에러 메시지와 함께 응답합니다.
- 정상적으로 처리되는 경우, 해당 User와 Seminar의 관계에서 is_active를 False로 설정하고, 그 시점을 dropped_at으로 저장하며, 세미나 생성 API와
동일한 구조의 body와 함께 `200`으로 응답합니다.
중도 포기한 세미나는 `POST /api/v1/seminars/{seminar_id}/user/me/`를 이용해 다시 참여할 수 없습니다. 이 경우 `400`으로 적절한 에러 메시지와 함께 응답합니다.
- 해당 세미나에 참여하고 있지 않은 경우, 무시하고 body와 함께 `200`으로 응답하면 됩니다.

### 10
- `assignment2`PR에 과제 관련 하고 싶은 말, 어려웠던 점 등을 남겨주세요. 물론 적극적으로 해결되어야 할 피드백이나
질문 사항은 [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 등을 이용해주세요!
- 구현을 하다가 과제 내용에 명시되지 않은 경우가 있다고 생각되면 [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 에서 질문해주세요.


## 제출 방식
- 과제1에서 생성한 waffle-rookies-19.5-springboot 레포지토리를 계속 사용할 예정입니다.
- 제출 방식은 과제1과 동일합니다. master(main) 브랜치에는 현재 과제의 스켈레톤 코드와 assignment1이 완성된 상태가 있어야 하며 그 상태에서 assignment2 브랜치를 분기해 작업해주시고 해당 branch PR을 날려주시면 됩니다.

- 마감 시점에 PR을 기준으로 과제 제출을 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.

- master branch의 상태는 반드시 [skeleton code](waffle_backend)와 동일해야합니다.

## 참고하면 좋은 것들
- 추후 점진적으로 추가 예정입니다.

- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.
- [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다. 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요.
또한 Issue 제목에 과제 내용의 번호 등을 사용하시기보다, 궁금한 내용의 키워드가 포함되도록 해주세요. 답이 정해져있지 않은 설계에 대한 고민 공유도 좋습니다.
- 문제를 해결하기 위해 질문하는 경우라면, 질문을 통해 기대하는 바, (가급적 스크린샷 등을 포함한) 실제 문제 상황, 이를 해결하기 위해 시도해본 것, 예상해본 원인 등을 포함시켜 주시는 것이 자신과 질문을 답변하는 사람, 제3자 모두에게 좋습니다.
- 저는 직장을 다니고 있으므로 아주 빠른 답변은 어려울 수 있고, 특히 과제 마감 직전에 여러 질문이 올라오거나 하면 마감 전에 모든 답변을 드릴 수 있다는 것은
보장하기 어렵다는 점 이해해주시면 좋겠습니다. 그리고 세미나 진행자들이 아니어도, 참여자 분들 모두가 자신이 아는 선에서 서로 답변을 하고 도우시려고 하면 아주 좋을 것 같습니다.
- 특히 '참고하면 좋을 것들'에는 추가 자료들을 첨부할 수도 있습니다. 때문에 종종 이 repository를 pull 받아주시거나, 이 페이지를 GitHub에서 종종 다시 확인해주시기 바랍니다.

