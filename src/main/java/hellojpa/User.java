package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

/*
    @Column(name = "TEAM_ID")
    private Long teamId;
*/

/*
    //다대일 (주인 O)    @ManyToOne //user 입장에서 many, team 입장에서 one이기 때문에 @ManyToOne 사용
    @JoinColumn(name = "TEAM_ID")
    private Team team;
*/

    //일대다 (주인 X) -> 변경을 막아서 읽기 전용으로 만든다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    //일대다
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

/*
    //다대다
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
*/

    //다대다 개선
    @OneToMany(mappedBy = "user")
    private List<UserProduct> userProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getUsers().add(this);
    }
*/
}
