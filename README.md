# cooking-app-spring

## 계획서

     https://peppered-estimate-067.notion.site/71de63e68df245bc93187039d681a12c

## 사용 API

     http://www.foodsafetykorea.go.kr/api/openApiInfo.do?menu_grp=MENU_GRP31&menu_no=661&show_cnt=10&start_idx=1&svc_no=COOKRCP01

## 기본 세팅

     1. cooking-app-android 저장소 Fork
     2. 본인의 cooking-app-android 저장소를 Clone (로컬의 저장소 폴더가 생성됨)
     $ git clone https://github.com/[자신의 계정이름]/cooking-app-android
     3. 팀의 cooking-app-android 저장소와 동기화 (변경된 내역을 나의 저장소에도 일치시켜주는 작업)

     # 먼저 로컬부터 동기화해줘야 한다. (Fork 하기 전의 레포. 즉, IFP 레포의 remote 이름을 "upstream"이라고 해준다.)
     # upstream 추가 -> 통상적으로 upstream이라고 해주는게 원칙이다.
     $ git remote add upstream https://github.com/college-capstone-team-1/cooking-app-android
     
     # upstream 레포의 변경 내역을 로컬의 저장소와 병합,코드 작업중 누군가가 새로운 파일을 push할 가능성이 있으므로 다시 최신화 시켜준다.
     
     $ git pull upstream [branch명]
     ex) 
     $ git pull upstream main


<br/>

## add,commit,push,pull request

     1. 작업완료시 add 해줍시다
     $ git add . 
     $ git add ./[파일경로]

     2. ".git" 폴더에 저장 (ex: git commit -m "TAG : 내용") -> "-m"은 message의 약자
     
     Git commit convention TAG 정리
     * Feat :	새로운 기능을 추가
     * Fix : 버그 수정
     * Docs : 문서
     * Chore : 빌드업무수정, 라이브러리 추가등
     * Refactor : 코드 리펙토링
     * Design :	CSS 등 사용자 UI 디자인 변경
     * Test :	테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음
     * Rename :	파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우
     * Remove :	파일을 삭제하는 작업만 수행한 경우
     
     ex)
     $ git commit -m "Feat : fragment 이동 추가"
   
     3. 본인이 Fork한 깃헙 저장소에 업로드 (ex: git push <Remote> <Branch>)
     $ git push origin main

     4. 본인이 Fork한 깃헙 저장소로 이동하여 Pull Request(PR)를 보낸다.
      ❗ 이때, 팀 저장소의 main 브랜치가 아닌 "복사한 레포지토리"로 보내야함
      이후 그룹장이 확인한 후 저장소의 브랜치로 병합시켜주는 작업을 하게 된다.

   
### ❗주의해야할 점
- **git pull**을 이용하여 코드작업 전, 후로 **최신화** 시켜주기
- **pull**을 사용할 때에는 **원본 레포지토리**를 이용한다.
- **push**을 할 때에는 **fork한 개인 레포지토리**를 이용한다.
