package jpabook.jpashop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO extends ItemDTO {

    private String author;
    private String isbn;

    public BookDTO(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        super();
    }
}
