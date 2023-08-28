package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm() {
        return "new-form"; // ModelAndView 대신 String만 반환해도 작동하도록 RequestMapping이 추상화되어있음
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username, // @RequestParam 어노테이션으로 간편하게 파라미터 파싱 가능
            @RequestParam("age") int age,
            Model model) { // 모델 객체가 필요하면 파라미터 추가 가능

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }
}
