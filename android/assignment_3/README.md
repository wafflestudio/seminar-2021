# Wafflestudio Android Seminar 2 - Assignment
#### due: 2021.10.17 24:00

## 과제 목표
- Android에서 HTTP 통신을 하는 방법을 알고 구현할 수 있다.
  - OkHttp, Retrofit, Moshi 의 역할을 이해한다.
  - HTTP Request와 Response의 구조를 이해하고 이용해볼 수 있다.
  - Json 형태의 Response를 해석하여 Android에서 사용할 수 있다. 
- Kotlin coroutines를 이용한 비동기 프로그래밍을 이해하고 구현할 수 있다.
- Repository를 이용하여 Database와 Network에 동시에 접근할 수 있다. 
- ViewType을 이용하여 다양한 디자인의 RecyclerView를 구현할 수 있다.
- (Glide를 활용하여 인터넷에서 사진을 가져와 보여줄 수 있다.)

## 과제 상세
- 사용자는 `MainActivity`에서 member list를 확인할 수 있다.
  - 각 item은 member name과 member team을 보여주어야 한다. team은 `iOS`와 `waffle`로 구분된다.
- 리스트의 각 member을 클릭 시 해당 member의 detail을 보여주는 `DetailActivity`로 이동한다.
  - DetailActivity는 member의 name, 그리고 member의 lecture list를 보여주어야 한다.
    - lecture list의 각 item은 title, instructor, credit(학점)으로 구성되어야 한다.
  - (Optional : ImageView에 member의 profile_image를 보여주어야 한다.)

- member 정보는 `https://jkhi75xm0a.execute-api.ap-northeast-2.amazonaws.com/waffle/members/` 를 통해서 가져온다.
- 각 member의 detail한 정보는 `https://jkhi75xm0a.execute-api.ap-northeast-2.amazonaws.com/waffle/members/{member_id}/`를 통해서 가져온다.
  -  ({member_id}에 원하는 member의 id를 넣어서 GET) 를 통해서 가져온다. (관련해서 Retrofit @Path 등의 내용을 찾아본다.)
- 뼈대 코드의 경우 이 레포지토리의 Assignment3Skeleton 폴더에 제공된다.
- 과제의 채점은 pass / fail 로 구체적인 기준은 두지 않고, 앞서 언급된 Member Application의 명세를 만족하면 pass 이다.
  - 다만, Repository Pattern, Room Database와 Retrofit 을 활용하여 완결성 있는 MVVM pattern의 앱을 작성해야 한다.
  - MVVM pattern은 뼈대 코드에 이미 구현되어 있다.

### 중요
- MainActivity에서 사용할 데이터는 Room Database에서 가져와야 한다.
  - 즉 MainActivity에서 api call을 해서 받아온 정보는 MainActivity 및 MainViewModel을 거치지 않고 Repository에서 Room Database로 insert해야 한다.
  - MainActivity의 데이터를 불러오는 순서는 다음과 같아야 한다.
  - `MainActivity.onCreate()에서 viewModel의 함수 call -> viewModel이 repository의 함수 call -> repository가 service의 함수 call -> service가 통신 -> 통신한 데이터를 repository가 받아서 dao에 함수로 넘겨줌 -> dao가 DB에 저장`
  - 위 방식으로 저장된 data를 MainActivity가 observe(collect)하여 구현해야 한다.
- DetailActivity에서 사용할 데이터는 API call을 해서 받아온 정보를 그대로 사용해도 무방하다.
- (Optional : profile_image에는 Glide라는 라이브러리를 사용한다. Glide에 관한 정보는 [공식 github page](https://github.com/bumptech/glide) 및 구글링을 통해 찾아본다.)

<img src="./assignment3example.gif" width="324" height="684" >

## 제출 방식
- 본인의 github에 생성했던 `waffle-android-assign` repository를 그대로 사용한다. 
- 기존에 사용하던 깃의 `master branch`에서 `git checkout -b assignment3` 등으로 새로운 branch로 이동한다.
- 다음의 방식을 사용하여 과제를 진행한다.
    - Android Studio를 통해 `waffle-android-assign` 폴더 안에 Assignment3 라는 이름의 새로운 프로젝트를 만들어 진행한다.
    - `wafflestudio/19.5-rookies repo`를 clone한 뒤, `android/assignment_3/Assignment3Skeleton` 폴더를 복사하여 진행한다.
- 과제를 완료한 후 `git add .`, `git commit`, `git push origin assignment2`를 통해 github에 업로드한 뒤, main(master) branch로 Pull Request를 만든다.
- 생성된 Pull Request에 [@veldic](https://github.com/veldic)을 Reviewers로 등록한다.

```
waffle-android-assign
├── Assignment0
├── (Assignment1)  // 있어도 되고 없어도 됨
├── (Assignment2)  // 있어도 되고 없어도 됨
├── Assignment3
    ├── app
    .
    .
    .
```

위와 같은 폴더 구조가 만들어지면 된다.

- 마감 시점의 assignment3 branch를 기준으로 세미나장이 직접 확인할 것입니다.

- Special Thanks to [JSKeum](https://github.com/JSKeum) for providing example data