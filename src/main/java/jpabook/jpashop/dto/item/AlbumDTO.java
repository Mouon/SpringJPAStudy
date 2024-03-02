package jpabook.jpashop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class AlbumDTO extends  ItemDTO{
    private String artist;
    private String etc;

    public AlbumDTO(Long id, String name, int price, int stockQuantity, String artist, String etc) {
        super(id, name, price, stockQuantity); // 필드 값 설정
        this.artist = artist;
        this.etc = etc;
    }
}
