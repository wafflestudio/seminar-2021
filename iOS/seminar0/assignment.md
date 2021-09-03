19.5-rookies Seminar 0 Assignment
================================

### **due: 2021.09.05(일) 23:59**

## 과제 목적
- Swift, Xcode, iOS 기본 UI 객체에 익숙해진다.
- Navigation Controller와 auto layout을 이해한다.

## 과제 - MVC 디자인 패턴이 적용된 로그인 뷰 만들기

- textfield를 통해 username, email, password를 입력 받습니다.
- '로그인' 버튼을 클릭하면 다른 view controller로 이동합니다. 이동한 view에서 입력 받은 username과 email을 보여줍니다
- username이 두 글자 이하일 때 로그인 버튼을 클릭하면 'username은 두 글자 이상이어야 합니다' 라는 메시지의 Alert view를 띄우고, 다음 뷰로 이동하지 않습니다.
- 비밀번호 textfield는 입력시 '***'와 같이 마스킹되어야 합니다.
- 세 개의 textfield는 하나의 UiView 또는 Stack View로 감싸져 있어야 합니다. (다음 그림을 참고해주세요)
<img width="559" alt="Screen Shot 2021-08-29 at 10 04 41 PM" src="https://user-images.githubusercontent.com/54926767/131252150-ec062f66-14b2-4860-a8a7-8cccc80b7337.png">

- 모든 view는 auto layout이 적용되어 있어야 합니다.
- User model을 구현해야 합니다. password는 property로 들어가지 않습니다.
- 디자인은 자유롭게 해주세요.


## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-ios` 이라는 이름으로 private repository를 개설합니다. 개설할 때 Add a README file을 체크해 주세요. 그리고 저희가 과제를 하시는 분이 누구인지 식별할 수 있도록 README file에 이름이 포함된 간단한 정보를 적어 주세요.

![image](https://user-images.githubusercontent.com/39977696/131165209-a6da208f-e12c-4e74-9d45-321916ded169.png)

2. 개설 후 Settings > Manage access 로 들어갑니다. (다음 그림을 참고해주세요)

![스크린샷 2020-08-30 02 13 52](https://user-images.githubusercontent.com/35535636/91642567-5eb9fe00-ea67-11ea-9382-89fcce03be70.png)

3. collaborator 로 세미나장 @jskeum 와 @Ethan-MoBeau 를 등록해 주세요. (다음 그림을 참고해주세요)

![스크린샷 2020-08-30 02 14 59](https://user-images.githubusercontent.com/35535636/91642588-87da8e80-ea67-11ea-9d5a-60a3596463c9.png)

4. `seminar-0-assignment` 브랜치에서 과제를 진행해 주세요. 앞으로 과제들은 같은 레퍼지토리의 다른 브랜치에서 진행하게 될 것입니다.

5. 과제 제출 시, main 브랜치로 pull request를 생성해 주세요.

6. 마감 시점의 pull request 를 기준으로 세미나장들이 직접 확인하고 피드백을 드릴 것입니다.

## 참고 자료
- Alert View : https://developer.apple.com/documentation/uikit/uialertcontroller
