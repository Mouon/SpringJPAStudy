package jpabook.jpashop.controller.item;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.dto.item.AlbumDTO;
import jpabook.jpashop.dto.item.BookDTO;
import jpabook.jpashop.dto.item.ItemDTO;
import jpabook.jpashop.dto.item.MovieDTO;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookDTO());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookDTO form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        model.addAttribute("itemId", itemId);
        ItemDTO form;
        if (item instanceof Book) {
            Book book = (Book) item;
            form = new BookDTO(book.getId(), book.getName(), book.getPrice(), book.getStockQuantity(), book.getAuthor(), book.getIsbn());
        } else if (item instanceof Album) {
            Album album = (Album) item;
            form = new AlbumDTO(album.getId(), album.getName(), album.getPrice(), album.getStockQuantity(), album.getArtist(), album.getEtc());
        } else if (item instanceof Movie) {
            Movie movie = (Movie) item;
            form = new MovieDTO(movie.getId(), movie.getName(), movie.getPrice(), movie.getStockQuantity(), movie.getDirector(), movie.getActor());
        } else {
            throw new IllegalArgumentException("알 수 없는 아이템 유형입니다: " + item.getClass());
        }

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    /**
     * 준영속 엔티티
     * */
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookDTO form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}





