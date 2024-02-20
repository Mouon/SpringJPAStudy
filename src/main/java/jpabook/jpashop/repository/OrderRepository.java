package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * 실무 스타일 아님!
     * */
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//
//            String jpql = "select o from Order o join o.member m";
//            boolean isFirstCondition = true;
//
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " o.status = :status";
//        }
//
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " m.name like :name";
//        }
//
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000);
//
//        if (orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            query = query.setParameter("name", orderSearch.getMemberName());
//        }
//
//        return query.getResultList();
//    }


    /**
     * 실무 스타일 !
     * Querydsl로 처리
     * */
    public List<Order> findAll(OrderSearch orderSearch) {
        BooleanBuilder builder = new BooleanBuilder();
        if (orderSearch.getOrderStatus() != null) {
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
        }

        return queryFactory.selectFrom(order)
                .leftJoin(order.member, member)
                .where(builder)
                .fetch();
    }
}

