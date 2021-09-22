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
    - `Context` 사용도 선택적으로 하면 된다.
  - 라이브러리
    - 라이브러리 제한이 완전히 풀린다. 사용하고 싶은 대로 사용하면 된다!
- 추가
  - 학생 코멘트 작성 기능 (피그마 참조)
    - 학생 정보에 댓글이 달아지는 내용이 있어야 한다.
    - 학생의 정보를 변경할 시 서버에서 코멘트를 자동으로 달아 준다. 따라서 학생의 정보를 수정할 시, 댓글 데이터를 다시 불러와야 한다.
    - 시간 다루는 라이브러리는 [`luxon`](https://moment.github.io/luxon/#/) 이나 [`dayjs`](https://day.js.org/) 중 취향에 맞는 걸 골라 사용하면 된다.
      - 단, ***"왜 그 라이브러리를 선택했는가"***에 대한 내용을 PR에 명시해야 한다.
  - 로그인
    - 서버가 생기면서 로그인이 생겼다.
- 필수는 아닌데 어차피 세미나 4 과제에서 필수 스펙으로 나올 거라 시간 남으면 미리 구현하면 좋은
  - `localStorage`를 이용한 자동 로그인 ([관련 읽어보면 좋은 글](https://velog.io/@0307kwon/JWT%EB%8A%94-%EC%96%B4%EB%94%94%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%B4%EC%95%BC%ED%95%A0%EA%B9%8C-localStorage-vs-cookie))
  - `cookie`를 이용한 `24시간 동안 안 보기` 버튼이 있는 팝업 (내용은 아직 디자인이 안 나왔으니 적당히 알아서)
  - 동명이인이 있거나 하는 등의 오류 발생 시 `window.alert` 대신 적당한 토스트 라이브러리 찾아서 토스트로 대체해서 사용자 경험 향상

## 참고사항
- 참고사항
  - 세미나 3 과제부터, 라이브러리 제한이 완전히 풀립니다. 사용하고 싶으신 대로 사용하시기 바랍니다!
  - 학생 추가 / 삭제 등의 로직을 이제 서버에서 처리하게 되면서, 데이터를 처리하는 로직이 좀더 간결해질 거예요

- API
  - 엔드포인트: `https://p04fpgjlo3.execute-api.ap-northeast-2.amazonaws.com/v1/`
  - API 문서: https://p04fpgjlo3.execute-api.ap-northeast-2.amazonaws.com/swagger
  - 로그인 api 제외, 모든 api는 헤더에 `Bearer 토큰`을 넣어야 정상 응답하고, 없으면 `401 UnAuthorized`를 던집니다.
  - 참고사항
    - 학생 리스트는 공동으로 쓰는 게 아니고, 할당된 계정별로 따로 있습니다.
    - 보안 수준이 아주 낮으니, 개인정보 등 중요한 정보는 작성하지 않으시는 걸 추천드립니다.
    - 난이도 조정을 위해, 서버에 `CSRF` 방어기능이 구현되지 않았습니다. 아직은 따로 대응하지 않으셔도 괜찮습니다.
    - `CORS` 는 모든 origin에 대해 완전히 오픈해 두었습니다.

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