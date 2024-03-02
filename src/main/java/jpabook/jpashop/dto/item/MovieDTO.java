package jpabook.jpashop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MovieDTO extends ItemDTO {
    private String director;
    private String actor;

    public MovieDTO(Long id, String name, int price, int stockQuantity, String director, String actor) {
        super(id, name, price, stockQuantity); // 필드 값 설정
        this.director = director;
        this.actor = actor;
    }
}
