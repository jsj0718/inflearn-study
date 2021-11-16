package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    /**
     * DI 3가지 방법
     */

    // 1. 필드 주입 (원하는대로 내용을 바꿀 수 있는 방도가 없다.)
//    @Autowired
//    private MemberService memberService;

    // 2. setter 주입 (public으로 노출되기 때문에 처음 setting한 값이 변경될 위험이 있다.)
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    // 3. 생성자 함수 주입 (Spring Container가 조립되는 시점에 원하는대로 변경이 가능하며, 한 번 생성 이후에는 변경이 불가능하므로 권장한다.)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
