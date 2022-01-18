package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
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
            member.setId(101L);
            member.setName("HelloJPA");

            //영속 상태
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            //1차 캐시에 저장된 경우 DB에 쿼리를 날리지 않는다.
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());
*/

/*
            //처음 조회 시 DB에서 가져오면서 1차 캐시에 등록되고, 그 다음 조회는 1차 캐시에서 가져온다. (동일성 보장)
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("result = " + (findMember1 == findMember2));
*/

/*
            //SQL 쿼리는 쓰기 지연 기능으로 인해 커밋 시에 한 번에 날라간다.
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("================");
*/

/*
            //영속성 컨테이너는 변화를 감지하여 자동으로 DB에 반영한다.
            Member findMember = em.find(Member.class, 150L);
            findMember.setName("AAA");
*/

/*
            //미리 데이터베이스에 반영하거나 쿼리를 보고싶은 경우 flush()를 강제로 호출하면 된다.
            Member member = new Member(201L, "Flush Test");
            em.persist(member);

            em.flush();
            System.out.println("=====================");
*/
            
/*
            //준영속 상태 (변경사항 수정 X)
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");
            
//            em.detach(member); //특정 엔티티만 준영속 상태로 만듦
            em.clear(); //영속 컨테이너 모두 초기화

            Member member2 = em.find(Member.class, 150L); //영속 컨테이너가 초기화되어 다시 select로 db에서 조회
*/

/*
            //@Enumerated에서 value Test (EnumType.String으로 지정해야 하는 이유)
            Member member = new Member();
            member.setId(1L);
            member.setUsername("정대만");
            member.setRoleType(RoleType.USER);
            em.persist(member);
*/

/*
            //IDENTITY 전략에서는 em.persist() 시점에 Query가 실행된다. (PK값을 알지 못하기 때문)
            //SEQUENCE 전략에서는 em.persist() 시점에 시퀀스 값을 가져와 PK값으로 등록 후 영속성 컨텍스트에 등록된다.
            Member member1 = new Member();
            member1.setUsername("member1");

            Member member2 = new Member();
            member2.setUsername("member1");

            Member member3 = new Member();
            member3.setUsername("member3");

            //SEQUENCE 전략에서 발생할 수 있는 성능 문제는 allocationSize 속성을 설정하여 개선 가능하다.
            System.out.println("=========================");
            em.persist(member1); //1, 51
            em.persist(member2); //메모리에서 호출
            em.persist(member3); //메모리에서 호출
            System.out.println("=========================");
*/

/*
            //객체를 테이블 데이터 중심 모델링 방식 (협력 관게 X)
            Team team = new Team();
            team.setName("북산");
            em.persist(team);

            User user = new User();
            user.setName("정대만");
            user.setTeamId(team.getId());
            em.persist(user);

            User findUser = em.find(User.class, user.getId());
            Team findTeam = em.find(Team.class, findUser.getTeamId());

            System.out.println("findUser.getName() = " + findUser.getName());
            System.out.println("findTeam.getName() = " + findTeam.getName());
*/

/*
            //객체지향 모델링 방식
            Team team = new Team();
            team.setName("북산");
            em.persist(team);

            User user = new User();
            user.setName("정대만");
            user.setTeam(team);
            em.persist(user);

            em.flush();
            em.clear();

            User findUser = em.find(User.class, user.getId());
            Team findTeam = findUser.getTeam();

            System.out.println("findUser.getName() = " + findUser.getName());
            System.out.println("findTeam.getName() = " + findTeam.getName());

            //양방향 매핑
            List<User> users = findUser.getTeam().getUsers();

            for (User u : users) {
                System.out.println("u.getName() = " + u.getName());
            }
*/

/*
            //연관관계 수정
            Team newTeam = em.find(Team.class, 100L); //100번 ID를 갖는 팀이 존재할 때
            findUser.setTeam(newTeam); //새로운 팀으로 수정 가능
*/

/*
            //연관관계 주인이 아닌 경우에 데이터 삽입 (삽입되지 않는다.)
            User user = new User();
            user.setName("정대만");
            em.persist(user);

            Team team = new Team();
            team.setName("북산");
            team.getUsers().add(user);
            em.persist(team);
*/

/*
            //다대일
            Team team = new Team();
            team.setName("해남");
            em.persist(team);

            User user = new User();
            user.setName("이정환");
//            user.changeTeam(team);
            em.persist(user);

            team.addUser(user);
//            team.getUsers().add(user);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<User> users = findTeam.getUsers();

            System.out.println("=================");
            for (User u : users) {
                System.out.println("u.getName() = " + u.getName());
            }
            System.out.println("=================");
*/

/*
            //instanceof 로 타입 비교 (프록시와 실제 객체를 ==으로 비교하면 false이기 때문)
            User user1 = new User();
            user1.setName("hello1");
            em.persist(user1);

            User user2 = new User();
            user2.setName("hello2");
            em.persist(user2);

            em.flush();
            em.clear();

            User findUser = em.find(User.class, user1.getId());
            User referenceUser = em.getReference(User.class, user2.getId());
            logic(findUser, referenceUser);
*/

/*
            User user = new User();
            user.setName("user");
            em.persist(user);

            em.flush();
            em.clear();

            User referenceUser = em.getReference(User.class, user.getId());
            System.out.println("referenceUser.getClass() = " + referenceUser.getClass());
            //초기화 여부 확인
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(referenceUser) = " + emf.getPersistenceUnitUtil().isLoaded(referenceUser));

            //프록시 객체가 준영속 상태일 때 초기화하면 오류 발생
//            em.detach(referenceUser);
//            em.close();
//            em.clear();
            
//            referenceUser.getName();
            Hibernate.initialize(referenceUser); //강제 초기화
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(referenceUser) = " + emf.getPersistenceUnitUtil().isLoaded(referenceUser));
*/

/*
            //즉시로딩과 지연로딩
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            User user = new User();
            user.setName("user1");
            user.setTeam(team);
            em.persist(user);

            em.flush();
            em.clear();
            
            //즉시로딩의 문제(N+1)을 fetch join으로 해결
            List<User> users = em.createQuery("select u from User u join fetch u.team", User.class).getResultList();
*/

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent); //CASCADE 설정 시 parent만 persist해도 child가 자동으로 persist 된다.
            em.persist(child1);
            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildren().remove(0); //고아 객체 설정 시 부모 엔티티와 연관관계가 끊어진 자식 엔티티는 자동 삭제
            em.remove(findParent); //고아 객체 설정 시 부모가 삭제되면 자식들도 자동으로 삭제된다.

            tx.commit(); //성공 시 커밋
        } catch (Exception e) {
            tx.rollback(); //실패 시 롤백
            System.out.println("e = " + e.getMessage());
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(User u1, User u2) {
        System.out.println("u1 == u2 : " + ((u1 instanceof User) && (u2 instanceof User)));
    }

    private static void printUser(User user) {
        System.out.println("user.id = " + user.getId());
//        System.out.println("user.name = " + user.getName());
    }

    private static void printUserAndTeam(User user) {
        System.out.println("user = " + user.getName());

        Team team = user.getTeam();
        System.out.println("team = " + team.getName());
    }
}
