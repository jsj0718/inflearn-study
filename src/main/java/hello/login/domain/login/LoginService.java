package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     *
     * @return null 로그인 실패
     */
    public Member login(String loginId, String password) {
/*
        Member member = memberRepository.findByLoginId(loginId).get();
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
*/
/*
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        return findMember.filter(member -> member.getPassword().equals(password))
                .orElse(null);
*/
        return memberRepository.findByLoginId(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }
}
