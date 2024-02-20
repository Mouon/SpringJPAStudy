package jpabook.jpashop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO extends ItemDTO {
    private String director;
    private String actor;

    public MovieDTO(Long id, String name, int price, int stockQuantity, String director, String actor) {
        super();
    }
}
