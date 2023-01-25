package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //select m from Member m where m.name = ? (By 뒤의 변수명을 통해 Query를 자동으로 작성해준다.)
    List<Member> findByName(String name);

}
