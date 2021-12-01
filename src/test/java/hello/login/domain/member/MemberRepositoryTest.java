package hello.login.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {
    MemberRepository memberRepository = new MemberRepository();

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test");
        member.setName("정대만");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember).isEqualTo(member);
    }
}