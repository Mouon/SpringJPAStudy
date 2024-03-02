package jpabook.jpashop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ItemDTO {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
