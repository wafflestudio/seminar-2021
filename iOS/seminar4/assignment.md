19.5-rookies Seminar 4 Assignment
================================

### **due: 2021.11.7(일) 23:59**

- 과제 pdf : [PDF](iOS/seminar4/seminar4.pdf)

## 과제 목적
- RxSwift의 기본적인 용법 공부
- Data Binding의 방법론 실습 (UICollectionView)
- OpenAPI 사용
- CocoaPods 이용한 오픈소스 사용

## 과제 - TodoList 만들기

1. Movie 탭
1) 헤더의 형태로 타이틀과 정렬 타입 선택이 가능한 버튼 존재 (Navigation Bar는 Hidden인 상태)
1-1) 정렬 타입은 최신 / 인기로 두 가지 -> 각각 API 콜을 하셔야 할 것임 (Get Latest, Get Popular)
2) CollectionView는 2열로 구성되어 있으며, API 기준 1페이지에 해당하는 영화의 정보가 화면에 표시됨 (포스터 + 제목 + 평점)
3) 스크롤을 가장 아래까지 내리면 다음 페이지 데이터를 불러오는 API를 호출하여 데이터를 추가로 로드하고, CollectionView를 갱신함
4) 영화를 선택하면 Custom Animation이 적용된 NavigationController의 push로 세부 정보 뷰가 뜸 (포스터 + 제목 + 평점 + overview) (Custom Animation 구현 및 적용은 추가 과제 : 필수 아님)
5) 세부 정보 오른쪽 위에는 별 버튼이 있어 favorite 여부 체크할 수 있도록 (Favorite 체크된 영화들은 로컬에 저장하여 재실행시에도 목록이 저장될 수 있도록 구현)

2. Favorite 탭
1) 상단엔 타이틀 + 즐겨찾기 등록된 영화의 개수 (HeaderView)
2) CollectionView의 형태로 1탭과 똑같이 영화 목록이 뜨도록 (이 데이터는 로컬에 저장된 Favorite 영화들의 데이터
3) 영화 선택 시 세부정보 뷰가 뜨고, 별을 눌러 Favorite 해제 가능
4) 만약 해제 했을 경우 뒤로 돌아갔을 때 목록에서 그 영화가 사라져야 함

## 제출 방식
1. `seminar-4-assignment` 브랜치에서 과제를 진행해 주세요. 

2. 과제 제출 시, main 브랜치로 pull request를 생성해 주세요.

3. 마감 시점의 pull request 를 기준으로 세미나장들이 직접 확인하고 피드백을 드릴 것입니다.

## 참고 자료
- MovieDBs API 링크 : https://developers.themoviedb.org/3
- UICollectionView 공식 문서 : https://developer.apple.com/documentation/uikit/uicollectionview
- RxDataSources 사용법 4가지 / 공식 문서 : https://eunjin3786.tistory.com/29 / https://github.com/RxSwiftCommunity/RxDataSources
- CocoaPods 공식 문서 : https://cocoapods.org/
- RxMarbles 예시 프로젝트 / 다이어그램 사이트 : https://github.com/RxSwiftCommunity/RxMarbles/tree/master/RxMarbles / https://rxmarbles.com/


