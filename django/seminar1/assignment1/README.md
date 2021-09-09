# 와플스튜디오 Backend Seminar[1] 과제

### due: 2021.09.11.(토) 12:00

### 과제 목적
- Django model을 수정하고 DB에 migrate하는 경험을 통해, ORM의 기본 개념을 익힙니다.
- CSRF 방어에 대한 개념을 이해한 채로, Django User 관련 기본 API들을 이해 및 개발합니다.
- 여러 table에 걸친 기본적인 로직을 갖춘 API를 개발하며, request의 body를 처리하고 response의 body에 적절한 데이터를 넣는 방식을 익힙니다.

### 주의할 점
- 이 repository를 이미 로컬에 clone해두었다면, pull을 통해 과제 시작 전 최신화한 버전을 이용하는 것을 잊지마세요.
- 이 repo를 clone하되 로컬에 생성된 [waffle_backend](waffle_backend)에서 바로 작업하지 마세요.
아래 '제출 방식'을 통해 생성한 본인의 `waffle-rookies-19.5-backend-1` repo를 로컬에 clone하고, 그 directory 바로 하위에 [waffle_backend](waffle_backend)
를 복붙하여 작업을 시작하세요.
- [seminar0](../seminar0) 내부의 waffle_backend와 착각하지 마세요! 또한 본인이 0번째 과제를 위해 기존에 작업했던 서버를 그대로 사용하지 마세요!
- 일반적인 경우 서로 다른 서버면 다른 Python 가상 환경을 쓰는 것이 맞고, 0번째 과제와 별도로 지금의 과제에 대해서는 별개의 Python 가상 환경을 구축하는 것이
바람직합니다. 하지만 사실상 0번째 과제와 같은 서버이므로, [requirements.txt](requirements.txt)에 추가된 패키지만 기존 환경에 추가로 설치하셔도 괜찮습니다.
- 로컬 환경의 MySQL의 기존 `waffle_backend` database의 table과 row들은 0번째 과제를 정상적으로 완료한 상태의 데이터를 그대로 갖고 시작하시면 됩니다.

### 과제 내용
1. `survey_surveyresult` table에 `user_id` column을 추가하세요. 이미 `survey_surveyresult`에 `user_id`가 없는 데이터들이 들어가있으므로,
`user_id`는 당연히 nullable해야 하며, 연결되어있는 user row가 삭제된다고 해도 survey_surveyresult의 해당 row까지 같이 삭제되면 안 됩니다.
해당 `ForeignKey`의 `related_name`은 `'surveys'`로 지정해주세요.(related_name이 무엇인지는 한 번씩 따로 찾아보시고 이해하려 노력하시면 좋겠습니다.)
덧붙여, `CharField`들엔 1번째 세미나에서 언급했듯 모두 `blank`만 `True`로 설정하시고, `timestamp`에는 이후 이 table의 row가 추가될 때마다 그 시점을 나타내는 값이
자동으로 들어가도록 관련 변경을 포함하시기 바랍니다. 이 조건들을 모두 충족하는 migration 파일이 하나로 이뤄져야 합니다.
migrate가 정상적으로 완료되었다면 Django `showmigrations` 명령어를 통해 console에 출력한 결과와 원하는 도구를 이용해
MySQL에 직접 `desc survey_surveyresult;`를 실행한 결과의 텍스트 또는 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요.
절대 DB에 직접 query를 실행해 column을 추가 또는 수정하지 말고, Django의 해당 model을
수정해 migration 파일을 생성하여 이를 적용시키는 방식으로 진행하세요. 만일, migration 파일을 잘못 추가하기만 한 경우에는 그냥 해당 Python 파일을 삭제하고 다시 생성하면 됩니다.
하지만 이미 DB에 migrate까지 진행했다면, 다시 model을 수정하여 또 새로운 migration 파일을 생성하지 말고, 잘못 진행했던 migrate를 Django 명령어를 이용해
기존 상태로 되돌린 후 해당 migration 파일을 삭제하고 진행하세요.

2. 이미 정의되어 있는 `POST /api/v1/user/`와 `PUT /api/v1/user/login`를 이용해 Postman으로 User의 회원가입과 로그인을 각각 진행하고 해당
request, response가 모두 보이는 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요. 회원가입과 로그인 API 모두 내부적으로 Django `login()`을
포함하고 있기에, 두 API 중 어떤 것이라도 정상 처리되는 시점에 [Django CSRF 문서](https://docs.djangoproject.com/en/3.2/ref/csrf/#how-it-works)에도 나와있듯
csrftoken이 변경되어 이를 신경 써야함을 알 수 있습니다. 이후 POST, PUT, DELETE 같은 method의 API 요청이 동작하지 못하면 이를 고려해야하는 것을 잊지마세요.
(관련 작년 이슈 [#105 issue](https://github.com/wafflestudio/rookies/issues/105) 참고)

3. 회원가입이 정상적으로 이뤄졌다면 당연히 MySQL에서도 이를 직접 확인할 수 있어야할 것입니다. 세 명 이상의 유저를 API로 회원가입시킨 후,
원하는 도구로 직접 DB를 조회해 모든 user들의 id, last_login, username, first_name, last_name, email, is_active, date_joined에 해당하는 column의 데이터만을 뽑아
텍스트 또는 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요.

4. 현재 이 서버는 회원가입 시에 first_name과 last_name에 해당하는 정보를 받을 수 없습니다. `POST /api/v1/user/`를 수정하여, first_name과 last_name을
request의 body로부터 받아 `auth_user` table에 새로운 row insert 시점에 함께 포함될 수 있도록 하세요.
first_name과 last_name 모두가 request의 body에 없다면(key 자체가 없거나, 있어도 빈 str) 기존처럼 회원가입시키면 됩니다.
하지만 둘 중 하나만 존재하거나, 둘 중 어느 것에라도 숫자가 포함되어 있다면 `400 BAD REQUEST`가 되어야 합니다. first_name, last_name을 포함해 회원가입하는 경우와,
400이 발생하는 회원가입 두 경우를 모두 Postman에서 진행하고 해당 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요. 물론 username, email, password는 항상 필수 조건입니다.
response에 이용되는 UserSerializer 역시 수정하여 first_name, last_name의 정보도 포함해 response로 전달하는 것을 잊지마세요.

5. 4.에서처럼 신규 회원가입 유저만 first_name, last_name 정보를 가질 수 있게 개발하면 기존 유저들의 항의가 있을 수 있을 것입니다. 이를 해결하기 위해 `PUT /api/v1/user/` API를
새로 개발하고, 이미 가입한 유저들이 해당 API를 이용해 first_name, last_name, 그리고 username을 수정할 수 있도록 하세요. username만 수정할 수도 있고,
first_name과 last_name만 수정할 수도 있고, 셋 다 동시에 수정할 수도 있습니다. 하지만 회원가입 때처럼 first_name, last_name 둘 중 하나만
변경 요청에 포함되거나, 둘 중 어느 것에라도 숫자가 포함되어 있다면 `400 BAD REQUEST`가 되어야 합니다. 로그인하지 않은 유저가 해당 API를 요청하면 `403 FORBIDDEN`처리해야 합니다.
username은 `desc auth_user;`를 MySQL에 실행해보면 알 수 있듯, unique 조건이 있습니다. 때문에 DB에 존재하던 username으로 바꾸려는 request는
아무 처리가 없으면 `500 INTERNAL SERVER ERROR`가 발생할 것입니다. 그런 경우가 발생하지 않도록 `409 CONFLICT` 처리하세요.
이 API에 대해서는 물론 Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.
response는 정상 처리되는 경우에만 `200 OK`와 함께 로그인 API의 body와 동일하게 처리하면 됩니다.

6. 유저가 로그아웃할 수 있도록 내부적으로 Django의 `logout()`을 활용한 `GET` 또는 `POST` `/api/v1/user/logout/` API를 개발하세요.( [#118 issue](https://github.com/wafflestudio/rookies/issues/118) 를 참고해주세요.) 로그인되어있지 않은 유저가
로그아웃 시도하면 `403 FORBIDDEN` 처리해야 합니다. 정상 처리되는 경우에는 `200 OK`와 함께 body는 비어있도록 하세요.
Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.

7. 유저가 자신의 정보를 확인할 수 있도록 `GET /api/v1/user/` API를 개발하세요. 로그인되어있지 않은 유저가
시도하면 `403 FORBIDDEN` 처리해야 합니다. 정상 처리되는 경우에는 `200 OK`와 함께 로그인 API의 body와 동일하게 처리하면 됩니다.
Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.

8. 이제 드디어 서비스상 유의미한 기능을 추가할 수 있습니다. 유저가 `POST /api/v1/survey/` API를 통해 설문조사에 참여할 수 있도록 하세요. 비교적 자유롭게
구현하셔도 되는데, python, rdb, programming, os는 request의 body에 key 이름으로서 빈 str 등이 아닌 값을 가진 채 필수로 포함되어야 하고
넷 중 하나라도 빠진 요청은 `400 BAD REQUEST`가 되어야 합니다. 또한 python, rdb, programming에 해당하는 값은 당연히 SurveyResult model 내부에도 포함되어있듯
1 이상 5 이하의 int로 변환되어 저장될 수 있어야 하며, 그렇지 않으면 마찬가지로 `400`입니다. body의 os에 대해서는 OperatingSystem의 id를 바로 받는 방식이 아니라,
OperatingSystem의 name에 해당하는 str을 받아 처리해야합니다. 기존에 `survey_operatingsystem`에 존재하는 name인 경우 그 OperatingSystem의 id를, 기존에 없는 name인 경우 새로운 OperatingSystem도 생성하여 그
id를 가져와 새로 생성되는 `survey_surveyresult`에 os_id로서 포함해 저장해야 합니다. 이에 대해서는 0번째 과제에서 사용한 `download_survey`에 해당하는 코드가 힌트입니다.
seminar1의 waffle_backend 서버에도 [해당 코드](waffle_backend/survey/management/commands/download_survey.py) 가 마찬가지로 포함되어 있습니다. 1.에서 진행한 migration 덕분에, SurveyResult 하나를 새로 생성할 때,
timestamp에는 해당 시점의 값이 자동으로 들어가야합니다. 또한 요청한 user의 id가 user_id로서 포함되어 저장되어야 합니다.(로그인 안 한 요청의 경우 `403`)
정리하자면, 이 API가 정상 처리되는 경우 항상 `survey_surveyresult`에 python, rdb, programming, os_id, user_id, timestamp에 null이 아닌 올바른 값이 포함되어
새로운 row가 insert되어야 합니다. `survey_operatingsystem`에는 request body의 os가 이미 존재하던 name에 해당하는지 아닌지에 따라 insert가 될 수도 안 될 수도 있습니다.
`survey_operatingsystem`에 insert할 때, description, price는 고려하지 않고 비워서 처리하면 됩니다.
설문 결과가 정상 생성되어 요청이 완료된 경우, `201 CREATED`와 함께, `GET /api/v1/survey/{surveyresult_id}/`에 해당하는 body와 동일하게 처리하면 됩니다.
한 유저가 여러 번 설문 결과를 제출할 수 있는 것은 정상적인 동작입니다. Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.

9. `SurveyResultSerializer`를 구현해뒀습니다. 해당 serializer 가 자신의 내부에 `'os'`처럼 `'user'`에 대한 내용도 포함해 serialize하도록 하세요. 이미 정의되어 있는 `UserSerializer`를 이용하시기 바랍니다.
해당 `survey_surveyresult`에 연결된 user가 없는 경우(직접 개발한 API를 통해 생성된 설문 결과 외에 `download_survey` command를 이용해 생성했던 경우)에는,
`'user'`의 값이 null로 response body에 포함되어야 합니다. Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.

10. `waffle-rookies-19.5-backend-1`의 `README.md`에 과제 관련 하고 싶은 말, 어려웠던 점 등을 남겨주세요. 물론 적극적으로 해결되어야 할 피드백이나
질문 사항은 [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 등을 이용해주세요!

## 제출 방식
[작년과 같은 방식을 사용할 예정입니다. 이미지는 작년 18.5 rookies 기준이므로 착오 없으시길 바랍니다. ]
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-backend-1`라는 이름으로 private repository를 개설합니다.

![스크린샷 2020-08-30 02 12 24](https://user-images.githubusercontent.com/35535636/91642533-097dec80-ea67-11ea-96e4-ab0dfa757187.png)

2. 개설 후 Settings > Manage access 로 들어갑니다.

![스크린샷 2020-08-30 02 13 52](https://user-images.githubusercontent.com/35535636/91642567-5eb9fe00-ea67-11ea-9382-89fcce03be70.png)

3. collaborator로, 세미나 운영진들을 초대합니다.

![스크린샷 2020-08-30 02 14 59](https://user-images.githubusercontent.com/35535636/91642588-87da8e80-ea67-11ea-9d5a-60a3596463c9.png)

- [@Jhvictor4](https://github.com/Jhvictor4) (아래 스크린샷에는 저 자신이라 포함이 안 되어있는 것이고, 당연히 추가하셔야 합니다.)
- [@gina0605](https://github.com/gina0605)
- [@dodo4114](https://github.com/dodo4114)
- [@PFCjeong](https://github.com/PFCJeong)

![스크린샷 2020-08-30 02 16 17](https://user-images.githubusercontent.com/35535636/91642619-cbcd9380-ea67-11ea-84ea-1a0729103755.png)

4. 아래 스크린샷과 같은 directory 구조를 갖추어야 합니다.

```
/README.md
/waffle_backend/manage.py
/waffle_backend/waffle_backend/*
/waffle_backend/survey/*
/waffle_backend/user/*
/results/
```

![스크린샷 2020-08-30 03 16 21](https://user-images.githubusercontent.com/35535636/91643553-3b934c80-ea6f-11ea-8e5c-c20b1e6e42a3.png)

![스크린샷 2020-08-30 03 16 29](https://user-images.githubusercontent.com/35535636/91643554-3cc47980-ea6f-11ea-9ade-087b4845df11.png)

5. 마감 시점에 master branch를 기준으로 collaborator로 지정된 세미나 운영진들이 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.

6. 가급적 repository 생성과 collaborator 지정은 미리 해둬주세요! 제출 방식을 다들 올바로 이해하고 계신지와 함께, 가능하다면 대략적인 진행상황을 보면서 필요하면 몇 가지 말씀을 더 드릴 수도 있습니다.

## 참고하면 좋은 것들
- 추후 점진적으로 추가 예정입니다.
- Django의 모든 [공식 문서](https://docs.djangoproject.com/en/3.2/)
- migration 관련해서는 model, field, migrate 관련해 여러 내용을 찾아보시고 신중히 진행해보시면 좋겠습니다! 특히 공식 문서의 [migrations](https://docs.djangoproject.com/en/3.2/topics/migrations/)와 [migrate](https://docs.djangoproject.com/en/3.2/ref/django-admin/#migrate)는 필수입니다.
- Django와 관련해 기본적인 것부터 너무 막막하다면, [Django tutorial](https://docs.djangoproject.com/en/3.1/intro/tutorial01/)을 따라가보시기 바랍니다. [한국어 버전](https://docs.djangoproject.com/ko/3.2/intro/tutorial01/)도 있습니다.
다만, 우리는 DRF를 프로젝트에 결합해서 사용하고 있음을 잊지마세요!
- [views.py](waffle_backend/survey/views.py)의 `list()`, `retrieve()`만으로 API endpoint가 만들어졌던 것처럼,
DRF의 [ViewSets](https://www.django-rest-framework.org/api-guide/viewsets/) 등 관련 문서를 참고하시면
어떤 식으로 POST, PUT method에 해당하는 view function을 정의할 수 있는지, logout에 해당하는 endpoint는 어떻게 만들 수 있는지 알 수 있습니다.
- https://docs.djangoproject.com/en/3.1/topics/auth/ 의 MIDDLEWARE 관련 설명을 보시면, `login()`을 했을 때 Django가 이후 요청에 대해서 어떻게 그 user를 아는 것인지 참고하실 수 있습니다. 
- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.
- [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다. 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요.
또한 Issue 제목에 과제 내용의 번호 등을 사용하시기보다, 궁금한 내용의 키워드가 포함되도록 해주세요.
- 문제를 해결하기 위해 질문하는 경우라면, 질문을 통해 기대하는 바, (가급적 스크린샷 등을 포함한) 실제 문제 상황, 이를 해결하기 위해 시도해본 것, 예상해본 원인 등을 포함시켜 주시는 것이 자신과 질문을 답변하는 사람, 제3자 모두에게 좋습니다.
- 저는 직장을 다니고 있으므로 아주 빠른 답변은 어려울 수 있고, 특히 과제 마감 직전에 여러 질문이 올라오거나 하면 마감 전에 모든 답변을 드릴 수 있다는 것은
보장하기 어렵다는 점 이해해주시면 좋겠습니다. 그리고 세미나 진행자들이 아니어도, 참여자 분들 모두가 자신이 아는 선에서 서로 답변을 하고 도우시려고 하면 아주 좋을 것 같습니다.
