package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/v1/{id}")
    public String findMemberV1(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //id를 기반으로 Member를 찾고 파라미터로 반환
    @GetMapping("/members/v2/{id}")
    public String findMemberV2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    /**
     * page 파라미터를 통해 데이터를 잘라서 가져올 수 있다. (기본은 20개)
     * size 파라미터를 통해 데이터의 수를 정할 수 있다.
     * sort 파라미터를 통해 정렬 기준과 내림차순/오름차순을 정할 수 있다.
     */
    @GetMapping("/members")
    public Page<Member> memberList(@PageableDefault(size = 5) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members/dto")
    public Page<MemberDto> memberDtoList(@PageableDefault(size = 5) Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

//    @PostConstruct
    public void init() {
        for (int i=0; i<100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
