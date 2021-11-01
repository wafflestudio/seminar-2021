# 와플스튜디오 Backend Seminar[4] 과제

### due: 2021.11.13.(일) 23:59

### 과제 목적
- AWS의 EC2, RDS 서비스를 이용해봅니다.
- Nginx, uWSGI를 활용해 정적/동적 콘텐츠를 배포하고, 좀 더 나은 배포 방식을 고민합니다.
- 도메인을 직접 구입해 EC2 instance에 연결하고, DNS와 HTTPS의 개념을 익힙니다.

### 주의할 점
- 본인이 [과제 2](https://github.com/wafflestudio/rookies/blob/master/backend/seminar2/assignment.md) 를 위해 생성한 private repository인 `waffle-rookies-18.5-backend-2`에서 이어서 작업합니다.
`test` branch의 최신 상황에서 `git checkout -b deploy`로 새로운 branch를 생성해 진행하세요.
- 배포를 위해 생성하고 수정한 파일들도 확인할 수 있도록 EC2 instance에서도 `deploy` branch에서 진행하고, 진행 내용을 commit해 push하세요.
- 과제 3을 온전히 완료한 상황에서 진행해야 합니다. 과제 3 당시의 `test` branch에 추가적인 변경을 가해서는 안됩니다.
- 다른 모든 것에 대해서도 마찬가지이지만, 배포에 관련해 인터넷에서 검색하여 나오는 자료들을 무비판적으로 따라하지 마세요. 어떤 프로젝트를 배포하려 하는지, 어떤 기술 스택을 이용하고 있는지, 어떤 OS를 이용하고 있는지, 어떤 의도를 가지는지 등에 따라 진행해야하는 것이 다를 것입니다.
- 제출 방식을 잘 지켜주세요. 제출 방식 때문에 자신이 의도한 마감 시점보다 이후에 commit하게 되는 경우에는, `deploy` branch의 `README.md`에 해당 특이 사항을 기재해주세요.
이러한 내용이 적절한 위치에 없으면 그러한 사유에도 인정하지 않겠습니다.

## 과제 내용

### 1
- 보강 자료 & 녹화 영상을 참고하여 EC2, RDS를 통한 서버 배포를 진행해봅니다.
- 자료에 나온 과정을 차근차근 밟기만 하면 달성할 수 있는 쉬운 과제입니다. 추가적인 설명이 필요할 것으로 생각되지는 않지만, 만약 있다면 적극적으로 질문해주시면 좋을 것 같습니다.
- 11.1일 기준 제공되는 녹화 영상에 틀린 정보가 있습니다.
- nginx가 정적 파일을 서빙하는 것은 맞지만, location 설정을 해주어야 어디에서 파일을 가져와서 보여줄 것인지 판단할 수 있습니다. 관련해서는 영상의 제 헛소리를 듣지 마시고, 업데이트 된 강의자료를 참고해주세요.

### 5
- 서버 코드가 변경되면 git pull을 하고, 필요에 따라 Nginx, gunicorn을 수동으로 재시작해야하는 상황이 번거로울 수 있습니다.
- 현재 배포는 원시적인 방법이지만 shell script를 잘 작성해두면 훨씬 편리하게 배포를 반복할 수 있습니다. 배포 과정을 더 잘 이해할 겸, 변경 사항이 있을 때마다 명령어 한 번만으로 원하는 배포 동작이 이뤄지도록 합시다.
`deploy.sh`라는 이름의 파일을 EC2 instance의 home(`/home/ec2-user`)에 생성해, shell script를 작성하세요. 크게 아래와 같은 동작들이 이루어져야 합니다.
  - `source ~/.bash_profile`
  - `waffle-rookies-19.5-backend-2`로 위치를 이동해 `git pull`하여 `deploy` branch에 변경 사항이 있으면 EC2 instance 내에서도 최신 상황 반영
  - `waffle-backend`로 위치를 이동해 Python 가상환경을 활성화시키고, `requirements.txt`의 모든 패키지 설치(최신 변경에서 패키지가 변경되었을 수 있으므로)
  - RDS의 database에 migration(최신 변경에서 migration 내용이 추가되었을 수 있으므로)
  - `python manage.py check --deploy` (deploy하기 적절한지 체크합니다.)
  - gunicorn 재시작
  - Nginx 설정 파일 체크
  - Nginx를 재시작
- 자신의 local에서 `deploy` branch에 서버 코드를 살짝 변경하는 commit을 한 후, EC2 instance에 접속해 `bash deploy.sh`만 입력하고 위의 과정이 모두 자동으로 잘 이뤄지는지 확인해보세요.
- `deploy.sh` 파일을 복사해 `waffle-rookies-18.5-backend-2/deployment` directory에 포함시키고, commit과 push하세요. 과제를 진행하며 최신 상황을 잊지 않고 반영하시기 바랍니다.

### 6 (자유과제)
- 예쁘지 않고 외우기도 어려운 IP 주소나 EC2에서 자동으로 생성해주는 domain 말고, 직접 domain을 만들어 우리의 EC2 instance에 연결해봅시다.
- 기본적으로 [가비아(Gabia)](https://www.gabia.com)를 이용하는 것으로 하겠습니다. 계정이 없으면 가입해주세요. (만일 다른 서비스를 이용하시는 경우에는 HTTPS까지 이용할 수 있는 도메인을 가질 수 있다면 그렇게 하셔도 됩니다. 이 경우에는 README.md 등에 명시해주시기 바랍니다. Issues에서 공유해주셔도 좋을 것 같습니다.)
- [도메인을 IP 주소에 연결하는 방법과 nslookup](https://medium.com/@bdv111/도메인을-ip-주소에-연결하는-방법과-nslookup-9e70a32eec57) 을 참고해 가비아에서 원하는 도메인을 구입하세요. (~~Medium 계정을 만들고 follow와 clap하는 것을 권장~~)
  - `.shop` 등의 최상위 도메인은 일반적으로 처음 1년에 500원이라는 저렴한 가격입니다.(18.5기 Rookies recruiting 페이지, 기억나시나요?)
  과제 4 완료까지 세미나를 중도 포기하지 않은 분들에 한해, 와플스튜디오에서 인당 500원을 지원해 환급해드리도록 하겠습니다. 물론 이보다 비싼 다른 최상위 도메인을 추가 본인 부담으로 이용하시는 것은 자유입니다.
  `deploy` branch의 `README.md`에 환급받으실 계좌번호, 은행 이름, 계좌 소유주를 명시해주세요.

- 이 시점에서(아직 동작하지 않는 상황이라도), 생성한 도메인에 `http://`가 포함되도록 하여 [rookies](https://github.com/wafflestudio/rookies)
[assignment-servers.md](https://github.com/wafflestudio/rookies/blob/master/backend/seminar4/assignment-servers.md) 자신의 `HTTP Domain`에 반영하도록 하여 Pull Requests에 추가로 적용하시길 바랍니다.

- 관련하여 `settings.py`의 `ALLOWED_HOSTS`에 도메인을 추가해주는 등의 수정이 필요할 수 있으며, Nginx conf 파일의 수정도 필요할 수 있습니다.

### 7 (자유 과제: 구매한 도메인으로 HTTPS 인증서 발급받아 적용하기)
- 단순한 IP 주소나 EC2에서 생성해주는 domain에는 HTTPS를 적용할 수 없습니다. 하지만 6.의 과정을 통해 이제 HTTPS 설정이 가능합니다.

- [Let's Encrypt](https://letsencrypt.org/ko/) 을 이용하여 구입한 도메인에 대해 HTTPS 인증서를 무료로 발급받도록 합시다.
EC2 instance 내에서 certbot을 이용하면 명령어만으로 모든 작업을 처리할 수 있어 편리합니다. (수동으로 관련 파일을 수정하는 등의 일은 해주어야 할 수 있습니다.)

- 이 시점에서(아직 동작하지 않는 상황이라도), 생성한 도메인에 `https://`가 포함되도록 하여 [rookies](https://github.com/wafflestudio/rookies)
[assignment-servers.md](https://github.com/wafflestudio/rookies/blob/master/backend/seminar4/assignment-servers.md) 자신의 `HTTPS Domain`에 반영하도록 하여 Pull Requests에 추가로 적용하시길 바랍니다.

- 관련하여 Nginx conf 파일의 수정이 필요합니다. 단순히 HTTPS가 지원되게 할 수도 있고, 더 똑똑하게 HTTP(80 port)로 오는 요청을 HTTPS(443 port)로 redirect 시킬 수 있습니다. 당연히 후자의 방식이 더욱 바람직합니다.

## 제출 방식 (반드시 지킬 것)
1. 과제 2를 통해 생성한 GitHub 개인 계정의 `waffle-rookies-18.5-backend-2` private repository에서 이어 작업합니다.

2. 과제 진행은 다음 절차를 따라주세요
  - **waffle-rookies-18.5-backend-2 의 `test`에서 `git checkout -b deploy` 로 이번 과제를 진행할 새로운 브랜치를 만들고 이동합니다.**
  - 해당 branch에서 작업을 완료해주세요. (**master, workspace, test branch에 push하면 안됩니다. deploy branch에만 변경사항을 반영해주세요.**)
  - 과제를 진행하면서 local 환경 및 EC2 환경 모두에서 deploy branch에 commit, push 해주시고 Pull Requests를 생성하시면 됩니다. (master <- deploy)
  - git이 어려운 경우 [OT자료](../../wafflestudio%2018.5%20rookies%20OT.pdf), https://backlog.com/git-tutorial/kr/stepup/stepup1_1.html 등을 참고해주세요.

  - 과제 진행 여부는 본인 서버의 **DNS 혹은 PUBLIC IP**에 접속해봄으로써 확인하려고 합니다. 이를 Readme.md에 추가해주세요.

3. 마감 시점에 PR을 기준으로 collaborators로 지정된 세미나 운영진들이 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.

4. master, workspace, test branch의 상태는 반드시 본인 과제 3의 제출 시점과 동일해야합니다. 

## 참고하면 좋은 것들
- 관련 문서(추후 점진적으로 추가 예정입니다.)
    - 세미나 3, 세미나 4 슬라이드의 참고 링크들
    - 과제 내용의 본문에서 언급된 링크들
    - https://stackoverrun.com/ko/q/8703618
    - https://docs.djangoproject.com/en/3.1/howto/static-files/deployment/
    - https://nachwon.github.io/django-deploy-4-static/
    - https://lahuman.github.io/nginx_location_options/

- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.
- [Issues](https://github.com/wafflestudio/rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다. 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요.
또한 Issue 제목에 과제 내용의 번호 등을 사용하시기보다, 궁금한 내용의 키워드가 포함되도록 해주세요. 답이 정해져있지 않은 설계에 대한 고민 공유도 좋습니다.
- 세미나 진행을 원활히 하고 있지 못한 것 같아 죄송한 마음이 듭니다. 군 문제 관련해서 개인적인 문제가 있던 상황이라 세미나에 집중하지 못했습니다. 비록 세미나 한 차시만을 남겨두고 있지만, 와플스튜디오에서 개발에 대한 긍정적인 경험을 만들어나가실 수 있도록 마지막까지 노력하겠습니다. 죄송하고, 감사합니다.


