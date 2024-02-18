## Querydsl과 동적 쿼리란?

### Querydsl
기존 Jpql 방식은 그러니까 쿼리를 String으로 쓰는 방식은 쿼리를 잘못쓰면 프로젝트를 빌드할때까지 오류를 모르거나,
심지어 api 호출 할때까지 오류 못잡는 경우가 비일비재, 
Querydsl은 Java로 작성된 쿼리 언어로, SQL을 사용하는 대신 Java 코드로 쿼리를 작성할 수 있게 해줌. 
인텔리제이같은 IDE의 지원을 받아, 컴파일 시점에 오류를 찾을 수 있으며, 타입 안전성을 보장.

### 동적 쿼리
동적 쿼리는 검색 조건이나 정렬 기준 등이 사용자 입력에 따라 달라지는 경우에 유용. 
예를 들어, 사용자가 검색어를 입력하거나 선택한 필터에 따라 쿼리의 조건이 동적으로 변경 가능. 
Querydsl은 이러한 동적 쿼리를 쉽게 작성할 수 있도록 도와줌.

### 코드 설명

```
public List<Order> findAll(OrderSearch orderSearch) {
    // BooleanBuilder 객체를 생성하여 동적 쿼리를 구성합니다.
    BooleanBuilder builder = new BooleanBuilder();

    // 주문 상태가 존재하는 경우, 해당 주문 상태를 검색 조건에 추가합니다.
    if (orderSearch.getOrderStatus() != null) {
        builder.and(order.status.eq(orderSearch.getOrderStatus()));
    }

    // 회원 이름이 존재하는 경우, 해당 회원 이름을 검색 조건에 추가합니다.
    if (StringUtils.hasText(orderSearch.getMemberName())) {
        builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
    }

    // Querydsl을 사용하여 쿼리를 작성하고 실행합니다.
    return queryFactory.selectFrom(order)
            .leftJoin(order.member, member)
            .where(builder) // 위에서 구성한 동적 조건을 쿼리에 적용합니다.
            .fetch(); // 결과를 리스트로 반환합니다.
}
```

### 상세 설명

1. **BooleanBuilder 생성**: `BooleanBuilder`는 Querydsl에서 동적 쿼리를 생성할 때 사용하는 빌더 클래스, `builder` 객체를 생성하여 동적 쿼리를 조립.

2. **검색 조건 추가**: `orderSearch` 객체에 따라 주문 상태와 회원 이름의 검색 조건을 동적으로 추가,
   `orderSearch.getOrderStatus()`와 `orderSearch.getMemberName()`을 사용하여 각 조건이 존재하는지 확인하고, 조건이 존재하는 경우에만 해당 조건을 `BooleanBuilder`에 추가.

4. **Querydsl을 사용한 쿼리 작성**: `queryFactory.selectFrom(order)`를 사용하여 `Order` 엔티티를 대상으로 하는 쿼리를 생성.
5. `leftJoin(order.member, member)`를 사용하여 회원과의 조인을 수행하고, `where(builder)`를 통해 위에서 구성한 동적 조건을 쿼리에 적용.
6. `builder.and`는 쿼링 조건 추가
7. `order.status.eq(orderSearch.getOrderStatus())`는 파라매터로 받은 OrderSearch 객체의 속성중 status를 통해 필터링 수행
8. **쿼리 실행**: `fetch()` 메서드를 호출하여 쿼리를 실행하고, 결과를 리스트로 반환.

### 이점

- **가독성**: Java 코드로 쿼리를 작성하기 때문에 IDE의 지원을 받을 수 있고, 코드 가독성이 뛰어남.
- **타입 안정성**: 컴파일 시점에 오류를 발견할 수 있고, 타입 안정성을 보장.
- **동적 쿼리 지원**: 사용자 입력에 따라 쿼리의 조건을 동적으로 변경 가능.

Querydsl을 사용하면 Java 언어로 쿼리를 작성할 수 있으므로 SQL을 사용하는 것보다 훨씬 직관적이고 안전하며 유연한 방식으로 데이터베이스와 상호 작용할 수 있습니다.

## build.gradle 설정

### 설정이 조금 복잡하니까 기록

```
buildscript {
	ext {
		queryDslVersion = "4.4.0"
	}
}

plugins {
	id 'org.springframework.boot' version '2.4.1'
	id 'io.spring.dependency-management' version '1.0.10.RELEASE'
	id 'java'
}

dependencies {
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
	annotationProcessor(
			"javax.persistence:javax.persistence-api",
			"javax.annotation:javax.annotation-api",
			"com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
	implementation 'com.querydsl:querydsl-jpa'
	annotationProcessor 'com.querydsl:querydsl-apt:4.4.0'
	implementation 'com.querydsl:querydsl-apt'
}
```

이런 흐름으로 설정

- 주의점 : `buildscript` 이게 `plugins`이거 앞에 와야함
