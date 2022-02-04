package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryOld memberRepository;

    @Test
    @Transactional //테스트 코드인 경우 DB를 롤백
    @Rollback(false) //롤백 하지 않고 커밋 실행
    void testMember() {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        memberRepository.save(member);
        Long memberId = member.getId();
        Member findMember = memberRepository.findOne(memberId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
    }

}