package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); //커맨드와 쿼리를 분리 (사이드 이펙트를 막기 위해 member를 반환하지 않는다.)
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
