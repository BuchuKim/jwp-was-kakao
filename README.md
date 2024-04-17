# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 목록
- [x] `/index.html` 파일을 서빙한다.
  - [x] 요청 헤더를 파싱한다.
  - [x] path가 `index.html`일 경우 해당 파일을 응답한다.
  - [x] 존재하지 않는 path 요청이나 null 값은 404로 응답한다.
- [x] CSS 파일을 지원한다.
- [x] `GET /user/create` 경로로 온 query string을 지원한다.
  - [x] key=value 형태로 전달된 query string을 `User` 객체로 매핑한다.
- [x] `POST /user/create` 경로로 온 회원가입 기능을 구현한다.
- [x] 리다이렉트 기능을 지원한다.
  - [x] 회원가입 완료 후 `/index.html`로 이동한다.
  - [x] 302 HTTP Status Code를 사용한다.

## 로그인 기능 목록
- [x] 회원가입한 사용자로 `/user/login.html`에서 로그인할 수 있어야한다.
  - [x] 로그인 성공 시 `index.html`로 리다이렉트한다.
  - [x] 로그인 실패 시 `/user/login_failed.html`로 리다이렉트한다.
- [x] 로그인한 유저가 `/user/login.html`에 GET 메소드로 접근했을 경우 `/index.html`로 리다이렉트한다.
- [x] 쿠키에 `JSESSIONID`가 없을 시 응답의 `Set-Cookie`에 `JSESSIONID`를 추가한다.
  - [x] Path 설정 값은 `"\"`를 사용한다.

## 사용자 목록 기능
- [x] 로그인하지 않은 사용자가 `/user/list`에 접근할 경우 로그인 페이지로 이동한다.
- [x] 로그인한 사용자가 `/user/list`에 접근할 경우 사용자 목록을 보여준다.
  -  [x] 사용자 전체를 조회 가능하다.
- [x] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.