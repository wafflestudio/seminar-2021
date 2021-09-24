19.5-rookies Seminar 3 Assignment
================================

### **due: 2021.10.09(토) 12:00**

## 과제 목적
- 서버와 HTTP 통신을 할 수 있다.
- 쿠키와 스토리지를 사용할 수 있다.

## 과제 스펙
- [피그마](https://www.figma.com/file/K5KY7htY5NVyDM1oa3INK0/wafflestudio-19.5-react-seminar-3?node-id=0%3A1)
- 변경
  - 각종 데이터
    - 이제 더미데이터를 사용하지 않고, 서버에 있는 database를 사용한다.
    - 이에 따라 추가, 수정, 정보 확인, 삭제, lock 등 모든 데이터 확인 및 조작이 서버를 통해 이뤄진다.
    - API 문서는 하단 참조
    - 학생 목록을 `Context`에서 관리하던 것도 이제 필수 사항이 아니다.
- 추가
  - 학생 코멘트 작성 기능 (피그마 참조)
    - 코멘트를 작성할 수 있어야 한다.
    - 학생의 정보를 변경할 시 서버에서 코멘트를 자동으로 달아 준다. 따라서 학생의 정보를 수정할 시, 댓글 데이터를 다시 불러와야 한다.
    - 시간 다루는 라이브러리는 [`luxon`](https://moment.github.io/luxon/#/) 이나 [`dayjs`](https://day.js.org/) 중 취향에 맞는 걸 골라 사용하면 된다.
      - 단, ***"왜 그 라이브러리를 선택했는가"*** 에 대한 내용을 PR에 명시해야 한다.
  - 로그인
    - 서버가 생기면서 로그인이 생겼다.
    - 자동 로그인이 되어야 한다.
      - `localStorage`를 이용하는 걸 추천한다 ([관련 읽어보면 좋은 글](https://velog.io/@0307kwon/JWT%EB%8A%94-%EC%96%B4%EB%94%94%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%B4%EC%95%BC%ED%95%A0%EA%B9%8C-localStorage-vs-cookie))
      - 세미나 2 과제에서 있었던 리다이렉트 문제 (`로그인되어 있을 때 잘못된 url ~~~`)도 이제 대응해야 한다.
    - JWT 토큰 인증 방식을 사용하며, 발급되는 토큰은 60시간 후 만료된다.
      - 만료 시 알려준다든가 자동 로그아웃이 된다든가 하는 기능은 이번 과제에서 확인하지 않는다.
      - 즉 로그인한 지 60시간이 지나서 토큰이 만료되어 학생 추가도 안되고 목록 받아와지지도 않고 등등의 문제가 발생해도 괜찮다.
- `cookie`를 저장소로 이용해서, `24시간 동안 안 보기` 버튼이 있는 팝업을 만들어야 한다.
  - 내용이나 디자인은 자유이다. 딱 봤을 때 정상적인 ui이면 괜찮다.
  - ***새로고침했을 때***, 이전에 `24시간 동안 안 보기` 버튼을 눌렀었고 그 이후로 24시간 이상이 지났으면 팝업이 다시 떠야 한다.
  - [이거](https://www.gov.kr/portal/main) 처럼
- 동명이인이 있거나 이름이 잘못되었거나 등의 오류 발생 시
  - 지금까지는 `window.alert`를 사용했다. 사실 `window.alert`를 막 쓰는 건 좋지 않다 ([참고](https://developer.mozilla.org/ko/docs/Web/API/Window/alert#%EC%B0%B8%EA%B3%A0))
  - `alert` 대신 적당한 토스트 라이브러리를 찾아서, 그걸로 대체한다.
    - [예를 들어 이런 거](https://www.npmjs.com/package/react-toastify)
- 코드 퀄리티
  - 강력한 필수 스펙입니다. 아주 중요하게, 모든 부분에서 지켜져야 합니다.
    1. `camelCase`, `PascalCase` 가 필수스펙이 됩니다.
    2. `useState` 안에 상수만 넣어야 합니다. props, context에서 꺼낸 값 등등 또는 그 값을 2차 가공한 값이 들어가면 스펙 오류입니다.
  - 필수 스펙입니다.
    1. `prettier` 적용해 주시기 바랍니다.
- 배포
  - 가령 cloudfront가 던지는 403 404 같은 에러들이 모두 해결되어야 합니다.

## 참고사항
- 이번 과제부터, 라이브러리 제한이 완전히 풀립니다. 사용하고 싶으신 대로 사용하시기 바랍니다!
- 학생 추가 / 삭제 등의 로직을 이제 서버에서 처리하게 되면서, 데이터를 처리하는 로직이 좀더 간결해질 거예요

## API
  - 엔드포인트: `https://p04fpgjlo3.execute-api.ap-northeast-2.amazonaws.com/v1/`
  - API 문서: https://p04fpgjlo3.execute-api.ap-northeast-2.amazonaws.com/swagger
  - 로그인 api 제외, 모든 api는 헤더에 `Bearer 토큰`을 넣어야 정상 응답하고, 없으면 `401 UnAuthorized`를 던집니다.
  - 발급된 JWT 토큰은 60시간 후 만료됩니다.
  - 참고사항
    - 학생 리스트는 공동으로 쓰는 게 아니고, 아래 내용으로 각자 발급해드린 계정별로 따로 있습니다.
      - username: github username
      - password: test
    - 보안 수준이 아주 낮으니, 개인정보 등 중요한 정보는 작성하지 않으시는 걸 추천드립니다.
      - password 암호화도 안 되어 있고, JWT 토큰도 해킹 가능합니다 (어차피 비밀번호도 전체공개인거)
    - 난이도 조정을 위해, 서버에 `CSRF` 방어기능이 구현되지 않았습니다. 아직은 따로 대응하지 않으셔도 괜찮습니다.
    - `CORS` 는 모든 `origin`에 대해 완전히 오픈해 두었습니다.

## 진행 및 제출 방식
- 세미나 2 과제와 동일

## 참고할 만한 링크
- 비동기
  - [벨로퍼트 Promise 강의](https://learnjs.vlpt.us/async/01-promise.html)
  - [(여유가 되신다면) 벨로퍼트 async / await 강의](https://learnjs.vlpt.us/async/02-async-await.html)
- HTTP
  - [HTTP 기초 설명하는 블로그](https://leehwarang.github.io/docs/tech/http.html)
- axios
  - [벨로퍼트 axios](https://react.vlpt.us/integrate-api/01-basic.html)
  - [Bearer 토큰 기초 사용법](https://devtalk.kakao.com/t/axios-get-headers/107134)
  - [Bearer 토큰 편하게 사용해보기](https://jihyehwang09.github.io/2019/01/29/javascript15/)
- 개발자 도구 네트워크 탭
  - [자세한 글](https://medium.com/%EB%82%B4%EC%9D%BC%EC%9D%98-%EC%9B%B9-%EA%B0%9C%EB%B0%9C/google-chrome-devtool-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%ED%83%AD%EC%9D%84-%ED%9A%A8%EC%9C%A8%EC%A0%81%EC%9C%BC%EB%A1%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-%EC%9C%84%ED%95%9C-7%EA%B0%80%EC%A7%80-%ED%8C%81-8d3166c5e273)
