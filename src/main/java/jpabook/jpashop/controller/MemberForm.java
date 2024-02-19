package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 엔티티는 최대한 순수하게 유지되어야한다.
 * 엔티티는 핵심 비즈니스 로직만을 갖고있고, 화면 관련 로직은 없어야함
 * 대부분은 DTO나 Form객체가 갑고있어야한
 * */
@Getter @Setter
public class MemberForm {

    /**
     *  이 어노테이션을통해 값이 비어있으면 오류가  발생하게 해줍니다
     * */
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
