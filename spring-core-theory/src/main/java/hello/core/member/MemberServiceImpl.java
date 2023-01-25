package hello.core.member;

import org.springframework.stereotype.Component;

//@Component("memberService") // Component에 별도의 이름 지정 가능, 아닐 시 default로 클래스 이름으로 지정됨 (앞글자만 소문자)
@Component
public class MemberServiceImpl implements MemberService {

    // 추상화에만 의존 (DIP 성립)
    private final MemberRepository memberRepository;

//    @Autowired (생성자가 하나일 때, @Autowired 생략 가능)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
