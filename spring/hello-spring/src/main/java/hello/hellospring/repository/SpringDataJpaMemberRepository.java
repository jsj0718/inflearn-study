package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //JPQL : select m from Member m where m.name = ?
    //인터페이스 이름만으로 JPQL을 자동으로 생성할 수 있다.
    @Override
    Optional<Member> findByName(String name);
}
