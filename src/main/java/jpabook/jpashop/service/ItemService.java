package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /** jap에서 관리하지않는 준영속 엔티티를 업데이트 하는 법
     *  변경 감지 이용
     * */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    /** jap에서 관리하지않는 준영속 엔티티를 업데이트 하는 법
     *  merge 이용
     *  merge 시에 모든속성을 변경함
     *  지정을 안해서 null이면 null로 업데이트할 위험이 있음
     *  그러니까 실무에서는 아래와 같은 merge 안씀
     *  set으로 하지말고 의미있는 매서드를 통해 구성하세요
     *  다시한번 말하지만 setter 쓰지말자
     * */
    public Item updateItem2(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(param.getName());
        findItem.setPrice(param.getPrice());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
    }


        public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
