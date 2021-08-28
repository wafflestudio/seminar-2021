19.5-rookies Seminar 0 Assignment
================================

### **due: 2021.09.05(일) 23:59**

## 과제 목적
- `HTML` 의 구조에 대해서 이해한다.
- `HTML` / `CSS` / `JS` 의 역할을 이해하고, 사용법에 익숙해진다.

## 과제 - `HTML` `CSS` `JS` 로 [와플고등학교 인원 관리 프로그램 간소화 페이지](http://rookies-seminar-0-assignment-example-website.s3-website.ap-northeast-2.amazonaws.com) 만들기
1. 구현하기 까다로운 만큼 스펙을 정말정말 많이 간소화했습니다.
   - 학생의 정보는 `이름` 만 있고, 기능 역시 `추가` 기능만 있습니다.
   - 학생 이름은 항상 한글 세 글자라고 가정할 수 있습니다.
3. 채점할 스펙은 다음과 같습니다.
   - 기능
     - 추가 버튼으로 추가가 되는가
     - 엔터키로 추가가 되는가
     - 이름을 입력하지 않고 추가할 시 window alert가 뜨는가
     - 헤더 좌우 이미지 클릭 시 링크가 정상적으로 작동하는가
   - 스타일
     - 각 컴포넌트의 위치가 적절한가
     - 한 줄에 다섯 학생이 제대로 나오는가
     - 다섯 학생 이후 줄바꿈이 잘 되는가
     - 학생이 너무 많아 칸 높이를 넘어갈 시 스크롤이 적용되는가
     - 기타 디자인에 마이너한 오류가 있는가
4. 기타 아래 항목도, strict하게는 아니어도 어느 정도 채점 결과에 반영합니다.
   1. 코드 리드미 및 주석을 잘 달았는가
   2. 변수 및 함수명이 적절한가
   - 물론 첫 과제이니만큼 이걸로 당락을 결정하기보단, 다음 과제부터 어떤 부분을 더 신경써달라고 피드백을 드리겠습니다.

## 주의사항
1. **커밋은 되도록이면 최대한 잘게 쪼개서 해주세요.** 커밋 내용을 지켜보고, 전체적인 진행 상황에 따라 과제 due나 명세를 수정할 수 있습니다.
1. 디자인은 완벽하게 동일할 필요는 없으나, 최대한 비슷하게 해 주시기 바랍니다.
    - 물론 더 예쁘게 만들어오신다면 환영입니다만, 커스텀하실 수 있는 건 레이아웃 말고, 폰트와 색깔 등 오로지 '스타일' 로 제한하겠습니다 (채점 편의상)
    - 즉 크기나 위치, 모양 등은 조정하지 말아 주세요.
1. **개발자 도구**를 사용하시면 과제하는 데 큰 도움이 될 것 입니다.
    - 단, HTML 과 css에 익숙해지는 것이 주 목적이니만큼, 제공해드린 예제 사이트의 구조를 복붙하는 등은 부정행위로 간주됩니다.
1. 가능한 한 꼼꼼하고 정확하게 주석을 달아 주시고, 리드미도 간략하게라도 작성 부탁드립니다.


## 개발자 도구 사용법
**이 방법은 크롬 기준입니다.**

1. 크롬을 켠 상태에서 아래 커맨드를 입력합니다. 아래의 사진과 같이 개발자 도구가 열립니다.
   >Windows: `F12`, `Ctrl` + `Shift` + `I`   
   >MacOS: `cmd` + `option` + `I`

![image](https://user-images.githubusercontent.com/48665265/91845284-d3718000-ec93-11ea-9617-15d0767cec95.png)

2. 최상단 메뉴바에서 `Elements`를 클릭하면 해당 페이지의 HTML을 파일을 볼 수 있습니다. (위 사진의 좌측 부분입니다.)
3. 원하는 태그를 클릭하면 우측 혹은 하단에서 CSS도 확인 가능합니다. (위 사진의 우측 부분입니다.)
4. 개발자 도구의 좌측 상단의 버튼을 클릭 후, 브라우저로 돌아가면 사진과 같이 원하는 영역을 선택할 수 있습니다. 개발자 도구를 확인하면 해당 태그가 선택된 것을 확인할 수 있습니다.

![image](https://user-images.githubusercontent.com/48665265/91845540-4a0e7d80-ec94-11ea-9fbf-7b2fda0aeeee.png)




## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-react-assignment-0` 이라는 이름으로 private repository를 개설합니다. 개설할 때 Add a README file을 체크해 주세요.

![image](https://user-images.githubusercontent.com/39977696/131165209-a6da208f-e12c-4e74-9d45-321916ded169.png)

2. 개설 후 Settings > Manage access 로 들어갑니다.

![스크린샷 2020-08-30 02 13 52](https://user-images.githubusercontent.com/35535636/91642567-5eb9fe00-ea67-11ea-9382-89fcce03be70.png)

3. collaborator 로 세미나장 @woohm402 와 TA @ars-ki-00 을 초대합니다.

![스크린샷 2020-08-30 02 14 59](https://user-images.githubusercontent.com/35535636/91642588-87da8e80-ea67-11ea-9d5a-60a3596463c9.png)

3. repository를 clone하고, `seminar-0-assignment`라는 이름의 브랜치를 master에서 체크아웃하고, 해당 브랜치에서 과제를 진행해 주세요.

3. 해당 브랜치를 푸시하고, master로 pull request를 생성해 주세요.
   - 과제를 다 하시고 생성하시든, 미리 생성하고 과제를 진행하시든 상관없습니다.
   - 이렇게 pull request를 생성하시지 않으면, 과제 채점이 힘들 수 있습니다.

4. 마감 시점의 pull request 를 기준으로 세미나장 및 TA가 직접 확인할 것입니다.

5. 가급적 repository 생성과 collaborator 지정은 미리 해둬주세요! 제출 방식을 다들 올바로 이해하고 계신지와 함께, 가능하다면 대략적인 진행상황을 보면서 필요하면 몇 가지 말씀을 더 드릴 수도 있습니다.


## 참고하면 좋은 것들
- 아래 3가지 사이트는 HTML/CSS의 태그 및 속성에 대해서 자세하게 설명되어 있는 사이트입니다. 참고하시면 과제에 도움이 될 것 입니다.
    - [한눈에 보는 HTML 요소(Elements & Attributes) 총정리](https://heropy.blog/2019/05/26/html-elements/)
    - [CSS 개요](https://happy-noether-c87ffa.netlify.app/presentations/level1/css/summary/)
    - [CSS 속성](https://happy-noether-c87ffa.netlify.app/presentations/level1/css/properties/)
- 아래 사이트는 원하는 태그의 속성, 예시 등을 더 자세히 볼 수 있는 사이트 입니다
    - [MDN](https://developer.mozilla.org/en-US/)
- 아래 사이트는 자바스크립트의 기본 문법에 대한 설명을 제공합니다.
    - [자바스크립트 공부](https://learnjs.vlpt.us/)
