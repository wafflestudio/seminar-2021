19.5-rookies Seminar 2 Assignment
================================

### **due: 2021.09.26(일) 23:59**

## 과제 목적
- Network를 통해 외부 서버에서 데이터를 받아 온다.
- Codable을 이해한다.
- Model을 효율적으로 설계하는 방법을 고민한다.
- Table View와 Table View Cell에 익숙해진다.
* 토이 프로젝트 / 팀 프로젝트를 하게 되면 외부 서버와 데이터를 주고 받는 작업이 중요해질 것입니다. 이 과제를 통해 Network 연결에 익숙해지길 바랍니다~

## 과제 - 고도화된 Waffle TT 만들기

- 메인 View Controller에서 Member 리스트 데이터를 fetch하고(/members/) 그 데이터들을 보여 주는 Table View를 만듭니다. Member 데이터에는 "team" 값이 포함되어 있는데, 각 "team" 에 맞게 Table View의 Section을 분리합니다. (그림 참고)
- 위에서 만든 Table View Cell을 터치하면 Navigation 위계에 따라 상세 프로필 View Controller로 이동합니다. 상세 프로필 View Controller에서 Member의 id를 이용한 Member 상세 데이터를 fetch하고(/members/{id}) 그 데이터들을 보여주는, Table View를 포함한 View들을 만듭니다. UIImage 객체를 이용해 profie_image를 불러와 보여주는 뷰도 만듭니다.(스켈레톤 파일 참고) 예시 그림에 있는 데이터들을 모두 보여주어야 하며, 그림에 포함되지 않은 데이터를 추가로 보여줘도 됩니다. (그림 참고)
- 위에서 만든 Table View Cell을 터치하면 Navigation 위계에 따라 강의 디테일 View Controller로 이동합니다. 예시 그림에 있는 데이터들을 보여주는 View(Table View를 사용해도 되고 사용하지 않아도 됩니다)를 만듭니다. (그림 참고)
- Navigation title을 구현해야 합니다. (title text는 View에 맞도록 자유롭게 써주시면 됩니다. Large title일 필요는 없습니다.)
- Member Model, Lecture Model은 반드시 구현해야 하고, 그 외에 필요하다고 생각되는 Model들이 있으면 구현합니다.
- Request 작업을 할 때 status code가 200이 아닌 경우 "서버에 문제가 발생했어요" 라는 메시지의 Alert view가 뜨도록 에러 처리를 해주세요.
- 디자인은 예시 그림과 동일할 필요는 없으니, 자유롭고 예쁘게 만들어 주세요.
- 모든 view는 auto layout이 적용되어 있어야 합니다.
* 과제에 필요한 서버 데이터 관련 내용은 밑에 넣었습니다.
* Network 작업은 세미나에서 다루었던 것처럼 URLSession 객체를 이용하시면 됩니다. 라이브러리인 Alamofire를 이용해서 구현해도 됩니다.

## 데이터 API
- 과제에 필요한 Network 관련 스켈레톤 코드를 Network.swift에 올려 놓았습니다.
- base URL : https://developer.apple.com/documentation/foundation/urlsession

- Member 리스트 불러오는 path : /members/
ex) 
- Request(GET) : https://developer.apple.com/documentation/foundation/urlsession/members/
- Response : 
```json
    {
        "statusCode": 200,
        "body": [
            {
                "id": 1,
                "name": "beomso0",
                "team": "waffle"
            },
            {
                "id": 2,
                "name": "KWSMooBang",
                "team": "waffle"
            },
            {
                "id": 3,
                "name": "MinnieMinwoo",
                "team": "iOS"
            },
        ]
    }
```

- 한 Member의 상세 정보를 불러오는 path : /members/{id}
ex) 
- Request(GET) : https://developer.apple.com/documentation/foundation/urlsession/members/1
- Response : 
```json
    {
    "statusCode": 200,
    "body": {
        "id": 1,
        "name": "beomso0",
        "team": "waffle",
        "profile_image": "https://waffle-ios.s3.ap-northeast-2.amazonaws.com/cat.png",
        "lectures": [
            {
                "classification": "교양",
                "category": "문화와 예술",
                "semester": 3,
                "instructor": "전예완",
                "lecture_number": "004",
                "course_number": "L0546.000500",
                "_id": "60f174d17fa06e00b481a9ef",
                "academic_year": "1학년",
                "course_title": "공연예술의 이해",
                "credit": 3,
                "class_time": "화(3-1.5)/목(3-1.5)",
                "class_time_mask": [
                    0,
                    14680064,
                    0,
                    14680064,
                    0,
                    0,
                    0
                ],
                "remark": "",
                "year": 2021,
                "department": "미학과",
                "quota": 80,
                "class_time_json": [
                    {
                        "place": "14-B101",
                        "_id": "60f174d17fa06e00b481a9f1",
                        "len": 1.5,
                        "day": 1,
                        "start": 3
                    },
                    {
                        "place": "14-B101",
                        "_id": "60f174d17fa06e00b481a9f0",
                        "len": 1.5,
                        "day": 3,
                        "start": 3
                    }
                ]
            },
        ]
    }
```

## 제출 방식
1. 자신의 GitHub 개인 계정에 `waffle-rookies-19.5-ios` 이라는 이름으로 private repository를 개설합니다. 개설할 때 Add a README file을 체크해 주세요. 그리고 저희가 과제를 하시는 분이 누구인지 식별할 수 있도록 README file에 이름이 포함된 간단한 정보를 적어 주세요.

![image](https://user-images.githubusercontent.com/39977696/131165209-a6da208f-e12c-4e74-9d45-321916ded169.png)

2. 개설 후 Settings > Manage access 로 들어갑니다. (다음 그림을 참고해주세요)

![스크린샷 2020-08-30 02 13 52](https://user-images.githubusercontent.com/35535636/91642567-5eb9fe00-ea67-11ea-9382-89fcce03be70.png)

3. collaborator 로 세미나장 @jskeum 와 @Ethan-MoBeau 를 등록해 주세요. (다음 그림을 참고해주세요)

![스크린샷 2020-08-30 02 14 59](https://user-images.githubusercontent.com/35535636/91642588-87da8e80-ea67-11ea-9d5a-60a3596463c9.png)

4. `seminar-2-assignment` 브랜치에서 과제를 진행해 주세요. 앞으로 과제들은 같은 레퍼지토리의 다른 브랜치에서 진행하게 될 것입니다.

5. 과제 제출 시, main 브랜치로 pull request를 생성해 주세요.

6. 세미나장 두 사람에게 request review를 해주세요.

7. 마감 시점의 pull request 를 기준으로 세미나장들이 직접 확인하고 피드백을 드릴 것입니다.

## 참고 자료
- UIImage : https://developer.apple.com/documentation/uikit/uiimageview
- URLSession : https://developer.apple.com/documentation/foundation/urlsession
