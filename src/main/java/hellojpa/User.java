package hellojpa;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

/*
    @Column(name = "TEAM_ID")
    private Long teamId;
*/

    @ManyToOne //user 입장에서 many, team 입장에서 one이기 때문에 @ManyToOne 사용
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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
}
