package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // 생성자에 자동으로 빈 주입
public class BasicItemController {

    private final ItemRepository itemRepository; // @RequiredArgsConstructor로 주입됨

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // DI 직후 수행되는 메서드
    public void init(){
        itemRepository.save(new Item("item1", 10000, 10));
        itemRepository.save(new Item("item2", 20000, 20));
    }


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/item";
    }

    @GetMapping("/add") // 실제 add 연산이 아니라 addForm 뷰 렌더링이기때문에 GetMapping
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model
    ){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item itemmmm){
        // 클래스명을 camelCase로 바꾼 "item"이 모델 Attribute에 등록된다(변수명과 관계 X).
        itemRepository.save(itemmmm);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        // 새로고침 시 중복 POST 요청을 피하기 위해 PRG(Post-Redirect-Get)
        return "redirect:/basic/items/" + item.getId(); // 다만 이방식은 한글이나 특수문자 등 사용 시 변수가 URL 인코딩이 안되기때문에 위험.
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId()); // URL 인코딩이 되도록 redirect에 변수를 등록해줌
        redirectAttributes.addAttribute("status", true); // redirect string에서 사용되지 않은 변수는 쿼리파라미터로 들어감(?status=true)
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; // redirect사용가능, itemId는 pathVariable 값을 그대로 사용함
    }

    @PostMapping("/{itemId}/delete")
    public String DeleteItem(@PathVariable Long itemId){
        itemRepository.delete(itemId);
        return "redirect:/basic/items";
    }

}
