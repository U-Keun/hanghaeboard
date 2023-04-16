# hanghaeboard
항해99 스프링 1주차 Lv.1 과제

## API 명세
|기능|method|url|request|response|
|------|---|---|---|---|
|전체 게시글 목록 조회|GET|/|-||
|게시글 작성|POST|/api/board|{ "username":"username", "userpwd":"userpwd", "title":"title", "contents":"contents" }|{ "success":boolean, "data":data }|
|선택한 게시글 조회|GET|/api/board|{ "id":id }|{ "success":boolean, "data":data }|
|선택한 게시글 수정|PUT|/api/board/{id}|{ "id":id, "username":"username", "userpwd":"userpwd", "title":"title", "contents":"contents" }|{ "success":boolean, "data":data }|
|선택한 게시글 삭제|DELETE|/api/board/{id}|{ "userpwd":"userpwd" }|{ "success":boolean, "data":null" }|
