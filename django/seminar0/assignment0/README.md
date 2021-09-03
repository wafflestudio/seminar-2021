# 와플스튜디오 Backend Seminar[0] 과제

### due: 2021.09.03 23:59

### 과제 목적
- 기본적인 서버와 DB의 개념과 관계를 이해합니다.
- 로컬 환경에 Python 가상환경, Django와 DRF를 포함한 Python 패키지들, MySQL, Database GUI 툴, 그리고 Postman을 설치하고 익숙해집니다.
- 직접 API를 호출하여 프론트엔드/클라이언트와 서버의 관계를 확인하고, Django를 활용해 기본적인 GET API를 개발해 봅니다.
- 관계형 데이터베이스가 데이터를 저장하는 방식에 익숙해집니다.

### 주의할 점
- 이 repository를 이미 로컬에 clone해두었다면, pull을 통해 과제 시작 전 최신화하는 것을 잊지마세요.
- 이 repo를 clone하되 로컬에 생성된 [assignment0](./)에서 바로 작업하지 마세요.
아래 '제출 방식'을 통해 생성한 본인의 `waffle-rookies-19.5-backend-0` repo를 로컬에 clone하고, 그 directory 바로 하위에 [assignment0](./)
를 복붙하여 작업을 시작하세요.

### 과제 내용
1. [seminar0](.)에 포함된 waffle_backend 서버를 자신의 로컬 환경을 잘 갖춰 동작 시키는 데에 성공시켜야 합니다. `python manage.py
runserver`를 통해 서버가 실행된 화면이 포함된 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요. 4. 이후 정도까지는 진행하신 후에야
서버를 완전히 정상 동작시킬 수 있을 것이고, 스크린샷도 이때 이후로 부탁드립니다. 참고로 터미널 등의 콘솔에 `runserver`를 시킨 직후의 화면 정도면 충분합니다.

2. 로컬 환경에 Python을 설치하고, Python 3.8.X이며 waffle_backend만을 위한 '가상환경'을 activate 시킨 상태로
[requirements.txt](./requirements.txt) 의 패키지들을 설치해야 합니다. 가상환경을 activate 시킨 것을 확인할 수 있고,
`pip list` 등을 통해 설치된 패키지와 버전을 확인할 수 있는 스크린샷들을 `/results`에 적절한 이름으로 포함시켜 주세요.
그리고 (사후 추가된 부분이라 필수는 아니지만) 가급적 `python --version` 등을 통해 Python 버전을 확인하는 과정도 포함되어 있다면 좋겠습니다.

3. 로컬 환경에 MySQL을 설치하여 waffle_backend의 [settings.py](./waffle_backend/settings.py) 에 명시된 DATABASES 설정대로 서버가 DB와 연결을 맺도록 해야합니다.
이 과정을 통해 로컬 MySQL에 올바른 password를 가진 user, database를 생성하고 user가 해당 database에 대한 권한(privileges)을 갖도록 할 것입니다.
가급적 MySQL CLI의 명령어를 통해 이를 진행하시기 바랍니다.

4. 자신의 로컬 DB에 [migration](https://docs.djangoproject.com/en/3.1/topics/migrations/) 을 완료해야 합니다.
이 과정을 통해 database에 table들이 추가됩니다. Django migration을 이용해 table을 추가하는 과정임을 확인하세요.

5. 이미 준비된 download_survey [command](https://docs.djangoproject.com/en/3.1/howto/custom-management-commands/) 를 이용해
[예시 데이터](./example_surveyresult.tsv)를 survey_surveyresult 테이블에 저장시켜야 합니다. 예시 데이터는 실제 여러분이 참여해주신 설문 결과이며,
애초에 익명이긴 하지만 사적인 내용이 포함되었을 수 있는 주관식 응답은 모두 제외했습니다. `python manage.py help`를 이용해 manage.py 를 통해 실행할 수 있는 command들을 확인할 수 있습니다.
기본 command들 외에, 제가 미리 추가해둔 `download_survey`를 확인할 수 있을 것입니다.
이 과정을 통해 database의 `survey_surveyresult`, `survey_operatingsystem` table에 row들을 insert하게 됩니다. Django command를 이용해 row를 추가하는 과정임을 확인하세요.

6. 앞선 3.~5.의 과정을 통해 로컬 MySQL에 database와 table, 그리고 row들이 잘 포함되었는지 확인하는 과정을 가져야할 것입니다.
두 가지 방식을 통해 직접 DBMS를 이용해 해당 내용을 조회하도록 하겠습니다. 먼저 MySQL CLI를 실행시켜서 MySQL CLI의 명령어들을 이용해, waffle_backend 서버의
database를 선택하고 해당 database에 속한 table들을 출력하세요. 그리고 `survey_surveyresult`, `survey_operatingsystem` table의 모든 row의
모든 column을 출력하세요. 이 과정이 포함된 스크린샷 또는 콘솔 로그를 `/results`에 적절한 이름으로 포함시켜 주세요. 이제 MySQL Workbench, DataGrip 등 Database GUI
툴을 이용해 마찬가지로 `survey_surveyresult`, `survey_operatingsystem` table의 모든 row의 모든 column을 출력하세요. 이 과정이 포함된 스크린샷 또는 콘솔 로그를 `/results`에 포함시켜 주세요.

7. waffle_backend 서버를 `8001` port로 실행한 후, Postman을 통해 `GET /api/v1/results/`와 `GET /api/v1/results/{surveyresult_id}/`를
호출하고 그 결과가 보이는 화면이 포함된 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요.

8. [views.py](./survey/views.py) 와 [urls.py](./survey/urls.py) 를 수정하여 `GET /api/v1/os/`와
`GET /api/v1/os/{operatingsystem_id}/`를 개발하세요. 이미 [serializers.py](./survey/serializers.py)
에 `serialize_survey_result`, `serialize_os`를 정의해두었기에 response 형식에 대해 고민할 필요는 없습니다.
`GET /api/v1/os/`는 DB의 모든 `survey_operatingsystem`을 클라이언트/프론트엔드에게 전달하는 API입니다. `GET /api/v1/os/{operatingsystem_id}/`는
같은 table에서 `operatingsystem_id`에 해당하는 primary key를 가진 row의 정보만 전달하는 API입니다. 두 API의 status code는 정상적인 경우 `200 OK`로 해주세요.


9. 이미 개발된 `GET /api/v1/results/`에 `os`라는 query params를 넣어 원하는 결과만 뽑을 수 있도록 변경해주세요. 예를 들어
    `GET /api/v1/results/?os=Windows` 라는 request를 날리는 경우 os가 윈도우라고 응답한 설문결과만을 반환해야 합니다. `os`에 들어갈 수 있는
    것은 'Windows', 'MacOS', 'Ubuntu (Linux)' 이며 다른 입력이 들어올 경우 response의 status code는 `400 Bad Request`를 반환해야 합니다. 
여기까지 개발한 내용을 아래 '제출 방식'에서 설명하는 repository에 `assignment0` directory로 포함시켜 주세요. 또한 개발한 두 API 역시 7.에서처럼 Postman을
통해 확인한 후 해당 스크린샷을 `/results`에 적절한 이름으로 포함시켜 주세요.

10. `GET /api/v1/os/{operatingsystem_id}/`를 개발할 때, `django.shortcuts`의 `get_object_or_404`를 사용하지 마세요.
직접 [get()](https://docs.djangoproject.com/en/3.1/ref/models/querysets/#get) 을 이용하고, 해당 id의 row가 존재하지 않는 경우 발생하는
Exception을 처리해 이 경우 별도의 data 없이 response의 status code가 `404 NOT FOUND`가 되도록 하세요. 일부러 존재하지 않는 id에 대해 Postman으로
API call을 하고, 그 결과에 대한 스크린샷 역시 `/results`에 적절한 이름으로 포함시켜 주세요.

11. `waffle-rookies-19.5-backend-0`의 `README.md`에 과제 관련 하고 싶은 말, 어려웠던 점 등을 남겨주세요. 물론 적극적으로 해결되어야 할 피드백이나
질문 사항은 [Issues](https://github.com/wafflestudio/rookies/issues) 등을 이용해주세요!

## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-backend-0`라는 이름으로 private repository를 개설합니다.
<img width="1670" alt="스크린샷 2021-08-27 오전 12 16 44" src="https://user-images.githubusercontent.com/48513130/130989627-3aa2366c-7418-430d-a9ee-5100b3571e19.png">

2. 개설 후 Settings > Manage access 로 들어갑니다.
<img width="1665" alt="스크린샷 2021-08-27 오전 12 17 15" src="https://user-images.githubusercontent.com/48513130/130989733-e1ce14ce-12a1-49b7-9c3f-6dad82345630.png">


3. collaborator로, 세미나 운영진들을 초대합니다.

- [@Hank-Choi](https://github.com/Hank-Choi)
- [@Jhvictor](https://github.com/Jhvictor4)
<img width="494" alt="스크린샷 2021-08-27 오전 12 19 17" src="https://user-images.githubusercontent.com/48513130/130990048-f4597ca8-08cb-4dc2-bc93-0f9dd8285f1f.png">


4. 아래 스크린샷과 같은 directory 구조를 갖추어야 합니다.

```
/README.md
/assignment0/manage.py
/assignment0/waffle_backend/*
/assignment0/survey/*
/results/
```

5. 마감 시점에 master branch를 기준으로 collaborator로 지정된 세미나 운영진들이 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.

6. 가급적 repository 생성과 collaborator 지정은 미리 해둬주세요! 제출 방식을 다들 올바로 이해하고 계신지와 함께, 가능하다면 대략적인 진행상황을 보면서 필요하면 몇 가지 말씀을 더 드릴 수도 있습니다.

## 참고하면 좋은 것들
- https://education.github.com/pack 에서 학생 인증하여 education pack을 받으시면 무료로 좀 더 많은 GitHub 기능들을 이용할 수 있습니다.
어차피 대부분의 기능들에 있어서는 거의 차이를 느낄 부분이 없긴 하지만, 미리 해두는 것이 좋습니다. 안 해두신 분들은 가급적 이번 과제 진행하시며 같이 완료하세요.
- Django의 모든 [공식 문서](https://docs.djangoproject.com/en/3.1/). 위에 명시되지 않은 링크이면서 과제와 직접적으로 관련해 읽으면 좋을 내용들은 [DATABASES](https://docs.djangoproject.com/en/3.1/ref/settings/#std:setting-DATABASES),
[urls](https://docs.djangoproject.com/en/3.1/ref/urls/) 등일 것 같네요.
- Django와 관련해 기본적인 것부터 너무 막막하다면, [Django tutorial](https://docs.djangoproject.com/en/3.1/intro/tutorial01/)을 따라가보시기 바랍니다. [한국어 버전](https://docs.djangoproject.com/ko/3.1/intro/tutorial01/)도 있습니다. 다만, 우리는 DRF를 프로젝트에 결합해서 사용하고 있음을 잊지마세요!
- JSON이 생소하신 분들은 [이 글](https://velog.io/@surim014/JSON이란-무엇인가) 등 관련 검색해서 읽어보시길 바라요.
- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.
- [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다!
다만 질문할 때에는 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요. 특히 이번에는 환경 구축을 시작하는 과정이 포함되므로 중요합니다.
- 문제를 해결하기 위해 질문하는 경우라면, 질문을 통해 기대하는 바, (가급적 스크린샷 등을 포함한) 실제 문제 상황, 이를 해결하기 위해 시도해본 것, 예상해본 원인 등을 포함시켜 주시는 것이 자신과 질문을 답변하는 사람, 제3자 모두에게 좋습니다.
- 저는 직장을 다니고 있으므로 아주 빠른 답변은 어려울 수 있고, 특히 과제 마감 직전에 여러 질문이 올라오거나 하면 마감 전에 모든 답변을 드릴 수 있다는 것은
보장하기 어렵다는 점 이해해주시면 좋겠습니다. 그리고 세미나 진행자들이 아니어도, 참여자 분들 모두가 자신이 아는 선에서 서로 답변을 하고 도우시려고 하면 아주 좋을 것 같습니다.
- 과제의 핵심적인 스펙은 바뀌지 않을 것이며 설령 있다면 공지를 따로 드릴 것입니다. 하지만 문구나 오타 수정 등의 변경은 수시로 있을 수 있고,
특히 '참고하면 좋을 것들'에는 추가 자료들을 첨부할 수도 있습니다. 때문에 종종 이 repository를 pull 받아주시거나, 이 페이지를 GitHub에서 종종 다시 확인해주시기 바랍니다.
