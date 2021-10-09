# 와플스튜디오 Backend Seminar[3,4] 과제

### due: 2021.11.15.(일) 23:59

### 주의할 점
- 본인이 과제 2를 위해 생성한 private repository에서 이어서 작업합니다.
`test` branch의 최신 상황에서 `git switch -c deploy`로 새로운 branch를 생성해 진행하세요. (`git checkout -b deploy`)
- 배포를 위해 생성하고 수정한 파일들도 확인할 수 있도록 EC2 instance에서도 `deploy` branch에서 진행하고, 진행 내용을 commit해 push하세요.
- 다른 모든 것에 대해서도 마찬가지이지만, 배포에 관련해 인터넷에서 검색하여 나오는 자료들을 무비판적으로 따라하지 마세요. 어떤 프로젝트를 배포하려 하는지, 어떤 기술 스택을 이용하고 있는지, 어떤 OS를 이용하고 있는지, 어떤 의도를 가지는지 등에 따라 진행해야하는 것이 다를 것입니다.
- 배포할 때에는 `java -jar -Dspring.profiles.active=prod seminar-0.0.1.jar`와 같은 command를 이용해 prod profile이 활성화되도록 해주세요

## 과제 내용
### 1
- 과제2에서 만든 api 동작을 확인할 차례입니다. test코드를 작성해 만드신 api가 정상적으로 동작하는지 확인하시면 됩니다. 테스트 코드의 coverage는 확인하지 않을 예정이지만 적어도 각 api의 성공케이스와 실패케이스 테스트하는 integration 테스트 하나씩, 큰 서비스(userService, seminarService)에 대한 unit test 하나 정도는 구성해주시면 좋을 것 같습니다.
- 또 배포하신 서버에 제 채점 코드를 수행했을 때 90% 이상 동작해야 합니다. 채점 코드는 과제2의 스펙에 나온 말들을 하나씩 1대1 대응할 예정입니다. 이슈에 올라왔던 애매한 지점들은 채점하지 않을 예정입니다. 채점 코드의 일부는 10월 23일에 공개하겠습니다. 과제 내용 1번은 배포가 끝난 후에 하셔도 됩니다.
- 테스트 코드보다 실제 클라이언트의 동작을 확인하는 것이 더욱 직관적일 수 있습니다. 빠른 시일내로 웹 클라이언트를 업로드 할 예정이니 배포하신 서버의 동작을 실제로 테스트해보세요
### 2
- [Amazon Web Services(AWS)](https://aws.amazon.com)의 EC2와 RDS 서비스를 이용하여 `seminar` 서버를 직접 배포합시다.
어려운 내용이 많을 수 있지만, 기본적으로 3번째 세미나 영상의 내용을 참고하면 많은 부분을 해결할 수 있을 것이라 생각합니다.
특정한 상황에서 배포를 하는 하나의 방식을 보여드렸을 뿐이니, 기계적으로 해당 내용을 참고하지 마시고 최대한 각각의 행동이 무엇을 의미하는지 이해하려 노력하세요. 고민을 하고 찾아보아도 어려운 내용이 있다면 [Issues](https://github.com/wafflestudio/rookies/issues)
에서 나눠주시면 됩니다.
- AWS 계정이 없는 경우에는 새로 생성하여 주시고, [Free Tier](https://aws.amazon.com/ko/free/free-tier-faqs/) 를 사용 가능하다면 1달의 EC2, RDS 사용량을 초과하지 않도록 `t2.micro`를 instance type으로 선택하시면 됩니다.
위의 링크와 기타 [관련 설명](https://aws.amazon.com/ko/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc) 등에도 있듯, EC2와 RDS
에 대해 t2.micro 기준 각각 한 달에 750시간(약 24 * 31)을 이용할 수 있으며, 따라서 t2.micro라도 각 서비스 내에서 복수 개의 instance를 동시에 운영하면 해당 범위를 초과하게 됩니다.
- AWS 프리티어가 없는 경우에는 새계정을 생성하시거나 Microsoft Azure, Naver cloud platform 등을 이용하면 무료 크레딧을 받을 수 있습니다. CentOS를 사용하시면 영상과 비슷하게 할 수 있습니다.

- EC2의 AMI를 택할 때, OS는 `Amazon Linux 2 AMI (HVM), SSD Volume Type`를 선택해주세요.
- RDS의 Engine으로는 `MySQL`을 선택해주세요.
- AWS의 각 자원들에는 Tag를 달아둘 수 있으며, 이것을 달아두는 것은 좋은 습관입니다. Tag의 Key로 `Name`을 설정하고, Value로는 `waffle-spring-seminar` 등을 달아두도록 합시다.
- EC2, RDS 둘 중 하나의 자원을 먼저 생성할 때, (VPC) Security Group을 `waffle-backend` 등의 이름으로 생성하도록 합시다.(특히 EC2의 자원 생성
Step 2 직후 바로 `Review and Launch`를 통해 완료하지 말고, `Configure Instance Details`를 통해 다음 단계를 계속 진행하도록 하세요.) 나중에 생성하는 자원에 대해서는
default Security Group 등을 사용하지 말고, 앞서 생성한 Security Group을 택해서 두 자원이 하나의 Security Group에 포함되도록 해주세요.
해당 Security Group에서 절대 SSH port를 모든 IP(`0.0.0.0/0` 등)에 노출하지 마세요. 또한 자신의 집 IP 등을 저장할 때 Description을 잘 기입하는 습관을 들이도록 합시다.
- EC2, RDS의 각 자원을 성공적으로 생성했다면 해당 instance들이 실행되고 있는 것을 AWS console에서 캡처해 `/results`에 해당 스크린샷을 적절한 이름으로 포함시키세요. 아래는 EC2의 예시입니다.
  - ![스크린샷 2020-10-18 18 33 55](https://user-images.githubusercontent.com/35535636/96363627-888ec780-1170-11eb-8478-33e086f8bd95.png)
- 이 시점에서(아직 동작하지 않는 상황이라도), 생성된 EC2의 Public IPv4 address, Public IPv4 DNS를 복사하여 springboot 폴더의 [url.md](https://github.com/wafflestudio/19.5-rookies/blob/master/springboot/url.md) 에 반영하는 Pull Requests를 open하시기 바랍니다.
![스크린샷 2020-10-18 20 08 27](https://user-images.githubusercontent.com/35535636/96365748-b75f6a80-117d-11eb-9291-8308090d8ed1.png)
- 만약 자신의 local 환경에 있는 MySQL 데이터를 RDS의 database에 그대로 옮기고 싶다면, mysqldump에 관해 찾아보시기 바랍니다.
- RDS 배포가 끝난 후에는 꼭 application.yml prod프로필에 대한 datasource url을 변경해주세요

### 3
- Nginx를 이용해 서버를 80번 포트(http 접속 포트)에 배포하세요.
- 가장 원시적인 방법 중 하나인, `git`을 이용해 해당 repository를 clone하고 변경 사항을 pull 해와서(`deploy` branch) 수동으로 배포하는 방식을 이용해 봅시다.
- 배포를 위해 생성하고 수정한 파일들도 확인할 수 있도록 EC2 instance 내에서도 `deploy` branch에서 진행하고, 진행 내용을 commit해 push하세요.
- Nginx와 uWSGI의 연결은 HTTP가 아닌 [unix socket](https://en.wikipedia.org/wiki/Unix_domain_socket) 방식을 이용하도록 하세요.
- 당연히 Python 가상환경을 이용해야 합니다. 또한 EC2 instance 내에서 파일 추가, 수정 등을 하기 위해서 [Vim](https://ko.wikipedia.org/wiki/Vim) 을 간단하게라도 이용할 줄 아는 것이 좋습니다.
- Nginx는 `/etc/nginx/nginx.conf`에 location block 등을 작성하여 동작시키지 말고, `/etc/nginx/sites-available/`과 `/etc/nginx/sites-enabled/` directory를 생성하고,
`/etc/nginx/sites-available/` 내에 conf 파일을 생성하여 symbolic link를 `/etc/nginx/sites-enabled/`에 포함시키는 방식으로 진행하세요.
이에 대해서는 React 배포에 대한 내용이긴 하지만 [이 글](https://medium.com/@bdv111/aws-ec2에서-nginx로-react-앱-직접-배포하기-c1e09639171e) 의 내용 4.에 더 자세히 설명되어 있습니다.
- nginx의 설정이 정상적으로 되었는지는 `sudo nginx -t` 명령어로 체크할 수 있습니다.
- EC2 instance의 메모리 한계를 경험하게 되는 경우가 있을 수 있는데 매우 느리겠지만 영상에서 설명한 것처럼 swapfile을 만들어 해결할 수 있습니다. 혹은 인스턴스를 재부팅하시면 해결할 수 있습니다. (`ps -ef --sort -rss` `top` `kill`등의 명령어를 이용해 해결하면 더욱 좋습니다.)

- 변경에 따라 배포된 환경에 잘 반영되는지 쉽게 확인하기 위해서, `GET /ping/` API를 생성하고 `pong`등의 매우 간단한 응답을 주도록 만들어두면 좋습니다.

- 배포에 성공했다면 postman을 이용해 정상작동 하는지 확인해보세요.
- 또한 RDS의 database에 MySQL CLI, MySQL Workbench, DataGrip 등으로 local 환경에서 연결해 `show tables`한 결과, 그리고 User, Seminar에 대항하는 table들 등을 몇 개 조회한 스크린샷도 `/results`에 적절한 이름으로 포함시키세요.

- 직접 수정하고 생성한 `/etc/nginx/nginx.conf`와 `/etc/nginx/sites-available/`의 conf 파일을 복사해 `seminar`(`deploy` branch)의 최상위에 `/nginx` directory를 생성하고
해당 파일들을 포함시키세요. 과제를 진행하며 최신 상황을 잊지 않고 반영하시기 바랍니다.

### 5
- 서버 코드가 변경되면 git pull을 하고(배포를 위해 EC2 instance에서만 추가 및 변경하는 파일과 충돌이 있다면 이것과 잘 merge 시키는 등의 과정이 필요할 수도 있을 것이지만, 우리는 `deploy` branch에 EC2의
내용도 모두 commit해 push하면서 반영했으니 sync가 맞는 상황일 것입니다. 상황을 단순화하기 위해 EC2 instance의 repository가 항상 `deploy` branch에 있도록 하세요.),
필요에 따라 uWSGI, Nginx(사실 Nginx는 매번 재시작할 필요는 없겠지만) 등을 수동으로 재시작해야하는 상황이 번거로울 수 있습니다.
- 현재 배포는 원시적인 방법이지만 shell script를 잘 작성해두면 훨씬 편리하게 배포를 반복할 수 있습니다. 배포 과정을 더 잘 이해할 겸, 변경 사항이 있을 때마다 명령어 한 번만으로 원하는 배포 동작이 이뤄지도록 합시다.
`deploy.sh`라는 이름의 파일을 EC2 instance의 home(`/home/ec2-user`)에 생성해, shell script를 작성하세요. 크게 아래와 같은 동작들이 이루어져야 합니다.
  - 레포지토리 루트 폴더로 위치를 이동해 `git pull`하여 `deploy` branch에 변경 사항이 있으면 EC2 instance 내에서도 최신 상황 반영
  - gradle을 이용한 빌드
  - jar 파일 실행
- 자신의 local에서 `deploy` branch에 서버 코드를 살짝 변경하는 commit을 한 후, EC2 instance에 접속해 `bash deploy.sh`만 입력하고 위의 과정이 모두 자동으로 잘 이뤄지는지 확인해보세요.

- `deploy.sh` 파일을 복사해 `seminar/scripts` directory에 포함시키고, commit과 push하세요. 과제를 진행하며 최신 상황을 잊지 않고 반영하시기 바랍니다.

### 6
- 예쁘지 않고 외우기도 어려운 IP 주소나 EC2에서 자동으로 생성해주는 domain 말고, 직접 domain을 만들어 우리의 EC2 instance에 연결해봅시다.
- [도메인을 IP 주소에 연결하는 방법과 nslookup](https://medium.com/@bdv111/도메인을-ip-주소에-연결하는-방법과-nslookup-9e70a32eec57) 을 참고해 가비아에서 원하는 도메인을 구입하세요.
- `.shop` 등의 최상위 도메인은 일반적으로 처음 1년에 500원이라는 저렴한 가격입니다.
- 혹은 [무료 도메인 이용](https://github.com/wafflestudio/18.5-rookies/issues/250)을 참고하시면 무료로 도메인을 이용할 수 있습니다.

- 이 시점에서(아직 동작하지 않는 상황이라도), 생성한 도메인에 `http://`가 포함되도록 하여 마찬가지로 19.5-rookies의 이 폴더 [url.md](https://github.com/wafflestudio/19.5-rookies/blob/master/springboot/url.md) 자신의 `HTTP Domain`에 반영하도록 하여 Pull Requests에 추가로 적용하시길 바랍니다.

### 7 (자유 과제: 구매한 도메인으로 HTTPS 인증서 발급받아 적용하기)
- 단순한 IP 주소나 EC2에서 생성해주는 domain에는 HTTPS를 적용할 수 없습니다. 하지만 6.의 과정을 통해 이제 HTTPS 설정이 가능합니다.

- [Let's Encrypt](https://letsencrypt.org/ko/) 을 이용하여 구입한 도메인에 대해 HTTPS 인증서를 무료로 발급받도록 합시다.
EC2 instance 내에서 certbot을 이용하면 명령어만으로 모든 작업을 처리할 수 있어 편리합니다. (수동으로 관련 파일을 수정하는 등의 일은 해주어야 할 수 있습니다.)

- 관련하여 Nginx conf 파일의 수정이 필요합니다. 단순히 HTTPS가 지원되게 할 수도 있고, 더 똑똑하게 HTTP(80 port)로 오는 요청을 HTTPS(443 port)로 redirect 시킬 수 있습니다. 당연히 후자의 방식이 더욱 바람직합니다.

### 8 (자유 과제: CI)
- github action을 이용해 CI를 구축해주세요. 브랜치가 잘 나뉘지 않았으므로 master 기준으로만 CI를 구축해주시면 됩니다.
- deploy 브랜치에 PR 날리는 경우 자동화된 테스트를 수행하면 됩니다.

### 9 (자유 과제: Docker를 이용한 배포)

    ```
    FROM openjdk:11-jdk-slim
    WORKDIR /app
    COPY . /app
    RUN ./gradlew bootJar
    EXPOSE 8080
    CMD java -jar -Dspring.profiles.active=prod build/libs/seminar-0.0.1.jar
    ```
  - 다음과 같은 Dockerfile을 만들고 이를 이용해 docker를 이용한 배포가 가능하도록 만들어주세요 (도커가 메모리 사용량이 높아 인스턴스에서 터질 수 있습니다. 메모리가 부족한 경우 이슈 남겨주세요)

## 제출 방식 (반드시 지킬 것)
1. 과제 2를 통해 생성한 GitHub 개인 계정의  private repository에서 이어 작업합니다.

2. 과제 진행은 다음 절차를 따라주세요
  - **waffle-rookies-18.5-backend-2 의 `test`에서 `git checkout -b deploy` 로 이번 과제를 진행할 새로운 브랜치를 만들고 이동합니다.**
  - 해당 branch에서 작업을 완료해주세요. (**master, workspace, test branch에 push하면 안됩니다. deploy branch에만 변경사항을 반영해주세요.**)

3. 이번 과제는 과제2, 과제3, 과제4가 정상적으로 수행됐는지 확인하는 통합 테스트입니다. 배포한 서버 url을 통해 제가 작성한 test코드를 돌려볼 예정이니 배포를 항상 최신화해주세요

4. 이번 코드 리뷰는 테스트 코드에 대해서만 리뷰하면 됩니다.

## 참고하면 좋은 것들
- 관련 문서(추후 점진적으로 추가 예정입니다.)
    - 과제 내용의 본문에서 언급된 링크들
    - https://lahuman.github.io/nginx_location_options/
