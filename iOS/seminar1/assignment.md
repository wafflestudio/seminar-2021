19.5-rookies Seminar 1 Assignment
================================

### **due: 2021.09.12(일) 23:59**

- 과제 pdf : [PDF](iOS/seminar1/ios-seminar-1.pdf)
## 과제 목적
- 가장 많이 사용하게 될 View Component 중 하나인 UITableView의 기본적인 사용법을 익힌다
- 데이터를 관리하기 위한 방법에 대해 고민해보는 시간을 갖는다 (로컬?, 서버?, View간 데이터 이동?, 싱글톤?, View Model?)

## 과제 - TodoList 만들기

- 해야 할 일을 등록하고, 완료 여부를 기록할 수 있는 기본적인 TodoList를 구현
- 앱을 껐다 키더라도 기존에 등록한 Todo Task들이 그대로 노출되어야 함
- NavigationBar Right Button에 버튼을 넣고 그 버튼을 탭할 경우 Task 추가 뷰로 이동하고, 그 뷰에서 입력 완료 버튼을 누르면 다시 List 뷰로 돌아오도록 구현 필요. 그리고 즉시 새로 추가된 Task가 List에 포함되어야 함.
- 각 Task에 해당하는 셀에는 Task의 이름과 완료 여부가 표시되어야 함.
- Task를 완료하기 위해서는 셀 우측에 있는 완료 버튼을 눌러야 함-> Done 되었을 경우 이름이 무언가 바뀌어야 함 (strikeThrough or text color 변화)

- 추가 기능 : 수정 + 삭제 기능 (어떻게 하면 좋을지는 구글링해서 찾아보시길…) -> 얘는 안 해오셔도 감점은 없으나 해오시면 어떤 식으로든 제가 권한 내에서 메리트를 드리겠습니다 (grace day 추가 등)
- 디자인은 자유롭게 해주세요.


## 제출 방식
1. `seminar-1-assignment` 브랜치에서 과제를 진행해 주세요. 

2. 과제 제출 시, main 브랜치로 pull request를 생성해 주세요.

3. 마감 시점의 pull request 를 기준으로 세미나장들이 직접 확인하고 피드백을 드릴 것입니다.

## 참고 자료
- 공식 문서 : https://developer.apple.com/documentation/uikit/views_and_controls/table_views
- RxDataSource (나중에 가르쳐 드릴 예정) : https://github.com/RxSwiftCommunity/RxDataSources
- Rx로 TableView 만들기 : https://eunjin3786.tistory.com/29
- TableView 개념 익히기 : https://zeddios.tistory.com/55?category=682195
- Codable 심화 : https://minsone.github.io/programming/swift-codable-and-exceptions-extension

(사실 이걸로 모자랄게 분명하니 구글링 열심히 해보세요~ 사실 아주 기본적인 내용들이라 구글링하면 한글로도 자료 많습니다)

