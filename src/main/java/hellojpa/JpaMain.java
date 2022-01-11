package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //emf, em, tx 등은 Spring Container에서 모두 관리해준다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //애플리케이션 로딩 시점에 1개만 생성
        EntityManager em = emf.createEntityManager(); //트랜잭션 단위마다 생성

        //JPA는 트랜잭션 단위에서 진행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/*
            //회원 등록
            Member member = new Member();
            member.setId(2L);
            member.setName("강백호");
            em.persist(member);
*/

            //회원 조회 및 수정
/*
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("서태웅");
*/
            
            //회원 삭제
/*
            em.remove(findMember);
*/

/*
            //전체 회원 조회
            List<Member> members = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1) //limit
                    .setMaxResults(10) //offset
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getId() = " + member.getId());
                System.out.println("member.getName() = " + member.getName());
            }
*/

/*
            //비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //영속 상태
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");
*/


            tx.commit(); //성공 시 커밋
        } catch (Exception e) {
            tx.rollback(); //실패 시 롤백
        } finally {
            em.close();
        }

        emf.close();
    }
}
