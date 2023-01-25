package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    /**
     * NamedQuery -> Spring Data JPA에서 제공하는 @Query로 완벽히 대체
     *
     * @Query(name = "")이 없는 경우
     * 1. 주어진 Entity에서 NamedQuery를 찾는다.
     * 2. 메서드명으로 쿼리를 생성한다.
     */
//    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //반환 타입이 굉장히 자유롭다.
    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); //단건

    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    /**
     * Pageable : Query에 대한 조건
     * Page : 반환 타입 -> 페이지 계산 가능 / Slice : 반환 타입 -> 더보기 기능 (totalCount 조회 여부)
     * countQuery 같은 경우 성능에서 취약점이 될 수 있기 때문에 left join 등을 할 경우에는 별도로 countQuery를 정의하여 사용할 수 있다. (count는 조인할 필요가 없기 때문)
     */
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m"
    )
    Page<Member> findByAge(int age, Pageable pageable);


    /**
     * Bulk Update
     *
     * @Modifying : executeUpdate() 를 실행시켜주는 Annotation (없으면 select절처럼 getResultList(), getSingleResult() 등이 실행된다.)
     * 벌크 연산 시 반드시 영속성 컨텍스트를 초기화해줘야 한다. (데이터 일관성이 깨질 수 있다.)
     */
    @Modifying(clearAutomatically = true) //자동으로 영속성 컨텍스트 초기화
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m join fetch m.team t")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints (@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(@Param("username") String username);

    //select for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(@Param("username") String username);

    <T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);

    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id, m.username, t.name as teamName " +
            "from member m left join team t",
            countQuery = "select count(*) from member",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
