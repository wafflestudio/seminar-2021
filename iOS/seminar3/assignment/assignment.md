19.5-rookies Seminar 3 Assignment
================================

### **due: 2021.10.11(월) 23:59**

## 과제 목적
- animation을 이해하고 UiView에 animation을 적용해본다.
- gesture를 이해하고 UIPanGeatureRecognizer를 사용해본다.
- UiPickerView를 사용해본다.

## 과제 - UiPickerView와 슬라이드 뷰가 구현되어 있는 간단한 앱 만들기
https://user-images.githubusercontent.com/54926767/135480836-fbd1bd51-b887-47e7-8bbc-74c923d1ef6c.mov

- 메인 View Controller에서 "세미나 선택하기" 버튼을 터치하면 UiPickerView가 들어 있는 View Controller가 modal 형태로 present되어 나타납니다.
- PickerView는 그림처럼 1개의 컴포넌트에 5개의 text row로 구성되어 있으며, 각 row text에 와플 세미나들의 이름을 넣습니다. (iOS, Android, Frontend, Django, Spring Boot)
- PickerView가 있는 View Controller에서 "선택하기" 버튼을 누르면 해당 View Controller가 dismiss되며, 선택한 세미나의 이름이 메인 View Controller에 나타나게 합니다. (delegate pattern을 참고해주세요!)
- 메인 View Controller 바닥에 100 ~ 200 height 높이의 View가 보이게 합니다. Bottom View의 원래 height는 500 ~ 'superView의 height' 가 되게 합니다.
- Bottom View를 Pan Gesture를 통해 위아래로 끌 수 있게 PanGestureRecognizer를 구현하고 적용합니다.
- Bottom View를 Pan Gesture로 끌어 올릴 때, ended 상태에서 끌어 올라간 높이가 해당 View의 height의 절반 값보다 크면 Title이 있는 데까지 해당 View를 올립니다.
- Bottom View를 다시 끌어 내릴 때, ended 상태에서 끌어 내려간 높이가 해당 View의 height의 절반 값보다 크면 다시 바닥에 내려가게 합니다. (처음에 있던 위치대로)
- Bottom View가 ended 상태가 되어 frame 값이 변경될 때 해당 View에 애니메이션이 들어가게 합니다. 애니메이션 속성 값은 자유롭게 해주세요.
- Bottome View 안에는 UIImageView를 넣어 주세요. 각자 Assets에 추가한 예쁜 이미지를 이 Image View에 넣어 주시면 됩니다.
- 모든 View에는 오토 레이아웃이 적용되어 있어야 합니다.
- 그림을 참고해주세요! 가이드에 나와 있지 않은 내용은 자유롭게 만들면 됩니다!

## 제출 방식
1. `seminar-3-assignment` 브랜치에서 과제를 진행해 주세요. 

2. 과제 제출 시, main 브랜치로 pull request를 생성해 주세요.

3. 세미나장 두 사람에게 request review를 해주세요.

4. 마감 시점의 pull request 를 기준으로 세미나장들이 직접 확인하고 피드백을 드릴 것입니다.

## 참고 자료
- UIPickerView: https://developer.apple.com/documentation/uikit/uipickerview
- UIPanGestureRecognizer: https://developer.apple.com/documentation/uikit/uipangesturerecognizer
- Animation: https://podcasts.apple.com/kr/podcast/8-animation/id1315130780?i=1000430722311&l=en
- Delegate Pattern: 세미나 1~2 자료
