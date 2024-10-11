# SPRING PLUS
코드 리팩토링 경험을 쌓기 위한 미니 과제

transactional(readOnly = true)가 설정되어있는 service에
저장 삭제 등의 메서드가 정상 작동하지 않는 문제가 발생.
해당 메서드들에 @Transactional을 붙여서 작동하고 변경이 반영되도록 수정

User 정보에 nickName 추가 구현
JWT 토큰에도 해당 컬럼이 포함되도록 JWTUtil클래스에서 createToken에 claim으로 nickName 추가

AOP가 잘못 설정 되어있는 코드 수정
해당 부가기능에 어노테이션이 @After로 잘못붙어있어 개발의도와 다르게 동작하는 것을 @Before로 수정

컨트롤러 테스트가 정상 작동 되지않는다.
컨트롤러 테스트를 작동시 예외를 확인해야하는 작업에서 BadRequest가 나와야하나 코드상 200ok가 나오는 상황
처음에는 200ok로 메서드가 정상 작동 후 예외문구가 제대로 나오는지 비교해야하는 것으로 생각하여,
다른접근방식으로 해결하려고해 시간이 좀 걸렸다.
하지만 예외가 발생하면서 BadRequest가 나와야 하므로 테스트 코드에서 200ok 로 설정되어있는 결과값을 bad request로 수정

JPQL을 사용하여 여러 조건으로 검색할 수 있는 검색기능 개발
검색 조건을 넣기 위해 RequestParam을 써보면서 더 공부 할 수 있었다.
JPQL을 구현하면서 query 작성하는 연습이 되었다.

cascade 기능을 사용하여 할일 생성시 생성한 유저가 자동으로 매니저로 등록되도록 수정

CommentController에서 getComments가 작동될때 N+1 문제가 발생
댓글들을 조회시마다 query문에서 참조된 user entity때문에 계속해서 query가 발생하는 상황
연관관계에서 user가 lazy loading으로 되어있는 상태에서 join으로 참조되어 일어난 일
join fetch를 통해 해당 query가 발생할때 user entity 데이터를 전부 가져오도록 수정

JPQL로 작성된 TodoRepository에 findByIdWithUser를 QueryDSL 로 변경하여 메서드의 가독성을 높혔다.
QueryDSL을 써보는 경험을 해보게 되었다.

Spring Security를 도입하면서 기존에 있던 filter를 대신해 security를 써보는 경험을 하게됨.

앞서 써본 QueryDSL을 조금 더 제대로 써볼수 있도록 새로운 조건의 검색기능을 개발
Projection을 활용하여 필요한 필드만 반환할수 있도록 개발하였다.

Transactional 전파옵션을 이용해 각각의 서비스가 독립적으로 처리될 수 있도록 개발
해당 service 메서드가 제대로 작동하는지 검사하기 위한 log를 저장하는 기능을 개발해보았다.

트러블 슈팅
검색을 위한 기간 설정에서 타입 불일치로 인한 에러
https://checkuu.tistory.com/133

N+1 문제를 해결하기
https://checkuu.tistory.com/134

컨트롤러 테스트 수정하면서 생긴 오류
https://checkuu.tistory.com/137

검색 기능 구현중 query문에서 fetchjoin으로 인한 오류
https://checkuu.tistory.com/138