package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberListReponseDTO;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * valid 어노테이션을 사용하면, 'JAVAX'의 Validation 가능을 사용하는 구나 라고 인지
     *
     * BindingResult result : 오류가 나면 오류가 담겨서 코드가 실행됨
     * result에 데이터 들어옴 -> 에러가 있으면 클라이언트 단에서 필드가 hasErrors이면
     * name 필드에대해서 에러메시지를 뽑아냄
     * 이때 에러가 있더라도 form에 데이터기 있기때문에 정보가 모두 담겨았습니다.
     * */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 뭔가 저장 후에 재로딩하면 안좋기때문에 리다이렉팅 시킴
    }

    /** DTO 로 바꾸는것을 추천합니다.
     * API를 만들때는 이유를 불문하고 엔티티를 외부로 반환하면 안됩니다.
     * */
    @GetMapping("/members")
    public String list(Model model) {
        List<MemberListReponseDTO> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
