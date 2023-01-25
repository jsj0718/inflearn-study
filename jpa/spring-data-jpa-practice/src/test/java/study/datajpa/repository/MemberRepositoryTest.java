package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        System.out.println("memberRepository = " + memberRepository.getClass());

        //given
        Member member = new Member("memberA");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 및 카운트 검증
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(memberRepository.count());

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        assertThat(memberRepository.count()).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        //then
        assertThat(members.get(0).getUsername()).isEqualTo("AAA");
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void findHelloBy() {
        List<Member> helloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    void testNamedQuery() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findByUsername("AAA");

        //then
        assertThat(members.get(0)).isEqualTo(m1);
    }

    @Test
    void testQuery() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findUser("AAA", 10);

        //then
        assertThat(members.get(0)).isEqualTo(m1);
    }

    @Test
    void findUsernameList() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        memberRepository.findUsernameList().stream()
                .forEach(System.out::println);

        //then
    }
    
    @Test
    void findMemberDto() {
        //given
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        memberRepository.save(m1);

        //when
        List<MemberDto> memberDto = memberRepository.findMemberDto();

        //then
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    void findByNames() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        //then
        members.stream().forEach(System.out::println);
    }

    @Test
    void returnType() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findListByUsername("AAA");
        Member findMember = memberRepository.findMemberByUsername("AAA");
        Optional<Member> findOptionalMember = memberRepository.findOptionalByUsername("AAA");
        
        //then
        members.stream().forEach(System.out::println);
        System.out.println("findMember = " + findMember);
        System.out.println("findOptionalMember.get() = " + findOptionalMember.get());
    }

    @Test
    void paging() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;


        //Spring Data JPA에서는 page가 0부터 시작한다. (offset, limit 대신 Pageable을 파라미터로 넘기면 해결)
        //Pageable은 주로 PageRequest를 통해 구현
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest); //반환 타입이 Page일 때 자동으로 totalCount 쿼리를 날린다.
//        Slice<Member> page = memberRepository.findByAge(age, pageRequest); //반환 타입이 Slice인 경우 limit + 1을 조회하고, 간편한 기능만 제공 (totalCount X)
//        List<Member> page = memberRepository.findByAge(age, pageRequest); //List로 바로 받을 수 있다. (정해진 수량만큼 데이터를 취하기만 할 때)

        //엔티티를 DTO로 반환 (Page로 사용 시 자동으로 json으로 반환이 된다.)
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        //then
        List<Member> content = page.getContent(); //페이지 내 데이터 꺼내기
        long totalElements = page.getTotalElements(); //totalCount 계산

/*
        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);
*/

        assertThat(content.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); //page number 계산
        assertThat(page.getTotalPages()).isEqualTo(2); //page 총 수 계산
        assertThat(page.isFirst()).isTrue(); //첫 page 여부 확인
        assertThat(page.hasNext()).isTrue(); //다음 페이지 여부 확인
    }

    @Test
    void bulkUpdate() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        memberRepository.save(new Member("member4", 40));
        memberRepository.save(new Member("member5", 50));

        //when
        int result = memberRepository.bulkAgePlus(25);

        //then
        assertThat(result).isEqualTo(3);
/*
        em.flush();
        em.clear();
*/

        Member member5 = memberRepository.findMemberByUsername("member5");
        System.out.println("member5.getAge() = " + member5.getAge());
    }

    @Test
    void findMemberLazy() {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();


        //when
//        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberFetchJoin();
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");

        members.stream().forEach(m -> System.out.println(m.getUsername() + ", " + m.getTeam().getName()));
        //then
    }

    @Test
    void queryHint() {
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
//        Member findMember = memberRepository.findById(member1.getId()).get();
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();

        //then
    }

    @Test
    void lock() {
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findLockByUsername("member1");

        //then
    }

    @Test
    void callCustom() {
        //given
        List<Member> result = memberRepository.findMemberCustom();

        //when

        //then
    }

    @Test
    void specBasic() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        Specification<Member> spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
        List<Member> result = memberRepository.findAll(spec);

        //then
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void queryByExample() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        //Probe (실제 도메인 객체)
        Member member = new Member("m1");
        Team team = new Team("teamA");
        member.setTeam(team);

        //ExampleMatcher
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age"); //조건에서 무시할 필드 선정

        //Example
        Example<Member> example = Example.of(member, matcher);

        List<Member> result = memberRepository.findAll(example);

        //then
        assertThat(result.get(0).getUsername()).isEqualTo("m1");
    }

    @Test
    void projections() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("m1", NestedClosedProjections.class);

        //then
        for (NestedClosedProjections nestedClosedProjections : result) {
            System.out.println("nestedClosedProjections = " + nestedClosedProjections);
        }
    }

    @Test
    void nativeQuery() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        Page<MemberProjection> result = memberRepository.findByNativeProjection(PageRequest.of(0, 10));
        List<MemberProjection> content = result.getContent();

        //then
        for (MemberProjection memberProjection : content) {
            System.out.println("memberProjection.getUsername() = " + memberProjection.getUsername());
            System.out.println("memberProjection.getTeamName() = " + memberProjection.getTeamName());
        }
    }
}
