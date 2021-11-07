# 와플스튜디오 SpringBoot Seminar[1] 과제. Updated At 2021.9.8 22:10

### due: 2021.09.11.(토) 23:59

### 과제 목적
- SpringBoot CRUD 어플리케이션 개발 이해

### 주의할 점
- 이 repository를 이미 로컬에 clone해두었다면, pull을 통해 과제 시작 전 최신화한 버전을 이용하는 것을 잊지마세요.
- 이 repo를 clone하되 로컬에 생성된 [seminar](seminar)에서 바로 작업하지 마세요.
아래 '제출 방식'을 통해 생성한 본인의 `waffle-rookies-19.5-springboot` repo를 로컬에 clone하고, 그 directory 바로 하위에 [seminar](seminar)
를 복붙하세요
- 저희는 리뷰 작업이 필요하므로 seminar를 복붙하고 master 브랜치에 commit, push 후 바로 assignment1이라는 브랜치로 가서 작업을 할 것입니다. master는 제가 작성한 코드와 완전 동일하게 유지해주시고 assignment1 브랜치만 계속 업데이트 해주세요.

### 과제 내용
1. 우선 `scripts`폴더의 `init-db.sh`을 실행시켜 주세요. 해당 쉘스크립트는 스프링 datasource config에 맞는 유저, 테이블을 생성해줍니다.
2. IntelliJ에서 `SeminarApplication.kt`의 main함수를 실행시키거나, `seminar` 폴더에서 `./gradlew bootRun` 명령어로 실행시킵니다.
ddl이 잘 db에 적용됐는지, 데이터는 잘 들어갔는지 확인합니다.
3. 스프링부트로 작성된 코드를 꼼꼼히 살펴보고 이해합니다. 특히 DI가 이뤄지는 부분, validation을 위한 어노테이션, 프로젝트 구조 등등을 살펴봅니다.
또 과제0에서 장고로 구현했던 4가지 api에 대해 postman을 사용해 테스트합니다.
이제 새로운 기능을 추가할 준비가 됐습니다.
4. 현재 구성된 웹 어플리케이션에 유저 도메인을 추가할 것입니다.
유저는 이름, 이메일을 가지고 있고 해당 두 값은 비어있거나 null이면 안됩니다. 추가적으로 이메일의 경우 중복되는 데이터가 존재하면 안됩니다. (spring의 validation과 Entity )

2. `survey_response` table에 `user_id` column을 추가하세요. 이미 `survey_response`에 `user_id`가 없는 데이터들이 들어가있으므로,
`user_id`는 당연히 nullable해야 합니다.
현재 스프링 프로젝트가 시작될 때마다 `application.yml`에 설정한대로 테이블이 drop되고 `DataLoader.kt`에서 기존 데이터가 추가됩니다.

4. 현재 이 서버는 유저 데이터가 없습니다. `POST /api/v1/user/`를 수정하여, 새로운 유저를 생성할 수 있는 api를 만들어주세요.
hint: 
    - ~~`@ModelAttribute` 어노테이션과 UserDTO를 사용해 request body를 받아주세요. request body의 content-type은 `form-data` 여야 합니다.~~
    - (2021/09/08 수정) json에서 snake_case의 객체 매핑을 쉽게 하기 위해 `@RequestBody` 사용하셔도 됩니다! (참고 이슈: [#328](https://github.com/wafflestudio/19.5-rookies/issues/328))
    - JpaRepository에서 기본 제공해주는 `save` 메소드를 이용해 db에 create가 가능합니다.
    - 실행 시마다 유저 데이터가 사라집니다. 원할한 테스트를 위해서 DataLoader에 기본 테스트용 유저 삽입 로직을 넣으면 디버깅이 편해집니다.

7. 유저가 자신의 정보를 확인할 수 있도록 `GET /api/v1/user/me/` API를 개발하세요. 이는 로그인한 유저가 자신의 정보를 알기 위함입니다. 저희는 아직 로그인 로직을 구현 안했으므로 단순히 header에 `User-Id`라는 custom header를 추가해 로그인한 유저를 나타냅니다.

8. 이제 드디어 서비스상 유의미한 기능을 추가할 수 있습니다. 유저가 `POST /api/v1/results/` API를 통해 설문조사에 참여할 수 있도록 하세요. 비교적 자유롭게
구현하셔도 되는데, spring_exp, rdb_exp, programming_exp, os는 request의 body에 key 이름으로서 빈 str 등이 아닌 값을 가진 채 필수로 포함되어야 하고
넷 중 하나라도 빠진 요청은 `400 BAD REQUEST`가 되어야 합니다. 또한 python, rdb, programming에 해당하는 값은 당연히 SurveyResult model 내부에도 포함되어있듯
1 이상 5 이하의 int로 변환되어 저장될 수 있어야 하며, 그렇지 않으면 마찬가지로 `400`입니다. body의 os에 대해서는 해당 이름과 같은 os가 없다면 `404 NOT FOUND`입니다.
timestamp에는 해당 시점의 값이 자동으로 들어가야합니다. 또한 요청한 user의 id가 user_id로서 포함되어 저장되어야 합니다.
설문 결과가 정상 생성되어 요청이 완료된 경우, `201 CREATED`와 함께, `GET /api/v1/results/{survey_response_id}/`에 해당하는 body와 동일하게 처리하면 됩니다.
한 유저가 여러 번 설문 결과를 제출할 수 있는 것은 정상적인 동작입니다. Postman 등으로 개발 과정에서 꾸준히 테스트하시되, 스크린샷을 포함시킬지는 자유이며 코드에 모든 내용을 반영하면 됩니다.

9. `SurveyResponseDto.Response`가 자신의 내부에 `'os'`처럼 `'user'`에 대한 내용도 포함해 반환하도록 하세요. 해당 `survey_response`에 연결된 user가 없는 경우에는,
`'user'`의 값이 null로 response body에 포함되어야 합니다.
10. `waffle-rookies-19.5-springboot` `assignment1`브랜치의 `README.md`에 과제 관련 하고 싶은 말, 어려웠던 점 등을 남겨주세요. 물론 적극적으로 해결되어야 할 피드백이나
질문 사항은 [Issues](https://github.com/wafflestudio/19.5-rookies/issues) 등을 이용해주세요!

## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-springboot`라는 이름으로 private repository를 개설합니다.

2. 개설 후 Settings > Manage access 로 들어갑니다.

3. collaborator로, 세미나장 및 리뷰 조원들을 초대합니다.

2. master 브랜치에 seminar 프로젝트를 복붙해서 커밋 후 바로 `assignment1`라는 브랜치를 생성 후 해당 브랜치에서 작업을 합니다. 

4. `assignment1` 브랜치는 아래 스크린샷과 같은 directory 구조를 갖추어야 합니다.

```
/README.md
/seminar/*
/results/*
```

5. 마감 시점에 assignment1 branch를 기준으로 제출 시간을 확인할 것입니다. GitHub repository에 반영되도록 commit, push해두는 것을 잊지 마세요.


## 참고하면 좋은 것들
- 추후 점진적으로 추가 예정입니다.

- **validation 부분을 제대로 알려드리지 못했는데 코드에 많이 포함되어 있습니다. @Valid 어노테이션을 단 곳 혹은 entity를 DB에 삽입하려고 할 때 자동으로 validation 검사를 시행합니다. 이를 통해 쉽게 잘못된 요청에 대해 400 response를 반환할 수 있습니다. **

- 아래 그림과 같이 `form-data`로 request body를 전송할 수 있습니다. 적절한 DTO와 @ModelAttribute 어노테이션을 사용하면 쉽게 DTO 오브젝트로 request body를 전달 받을 수 있습니다.
 <img width="605" alt="스크린샷 2021-09-05 오전 2 17 55" src="https://user-images.githubusercontent.com/48513130/132103089-010135e6-2b63-4caa-8f84-c5c14f5c81fc.png">

- raw - json 을 선택해서 json을 전달할 수도 있습니다! 이 때는 `@RequestBody` 어노테이션으로 DTO로 매핑하면 됩니다.

- https://www.baeldung.com/ 

- [Validation 관련 문서](https://www.baeldung.com/spring-service-layer-validation)

- [Header 받는 법](https://www.baeldung.com/spring-rest-http-headers)

- https://docs.spring.io/spring-boot/docs/2.5.4/reference/pdf/spring-boot-reference.pdf

- 앞으로도 늘 그렇겠지만, 과제를 진행하며 모르는 것들과 여러 난관에 부딪히리라 생각됩니다. 당연히 그 지점을 기대하고 과제를 드리는 것이고, 기본적으로 스스로 구글링을
통해 여러 내용을 확인하고 적절한 수준까지 익숙해지실 수 있도록 하면 좋겠습니다.

- [Issues](https://github.com/wafflestudio/rookies/issues) 에 질문하는 것을 어려워하지 마시길 바랍니다. 필요하다면 본인의 환경에 대한 정보를 잘 포함시켜주세요.
또한 Issue 제목에 과제 내용의 번호 등을 사용하시기보다, 궁금한 내용의 키워드가 포함되도록 해주세요.
- 문제를 해결하기 위해 질문하는 경우라면, 질문을 통해 기대하는 바, (가급적 스크린샷 등을 포함한) 실제 문제 상황, 이를 해결하기 위해 시도해본 것, 예상해본 원인 등을 포함시켜 주시는 것이 자신과 질문을 답변하는 사람, 제3자 모두에게 좋습니다.
- 저는 직장을 다니고 있으므로 아주 빠른 답변은 어려울 수 있고, 특히 과제 마감 직전에 여러 질문이 올라오거나 하면 마감 전에 모든 답변을 드릴 수 있다는 것은
보장하기 어렵다는 점 이해해주시면 좋겠습니다. 그리고 세미나 진행자들이 아니어도, 참여자 분들 모두가 자신이 아는 선에서 서로 답변을 하고 도우시려고 하면 아주 좋을 것 같습니다.
- 과제의 핵심적인 스펙은 바뀌지 않을 것이며 설령 있다면 공지를 따로 드릴 것입니다. 하지만 문구나 오타 수정 등의 변경은 수시로 있을 수 있고,
특히 '참고하면 좋을 것들'에는 추가 자료들을 첨부할 수도 있습니다. 때문에 종종 이 repository를 pull 받아주시거나, 이 페이지를 GitHub에서 종종 다시 확인해주시기 바랍니다.
