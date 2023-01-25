package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

//    다대일 (주인 X)
//    @OneToMany(mappedBy = "team")
//    일대다 (주인 O)
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<User> users = new ArrayList<>(); //관례

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

/*
    //편의 메서드
    public void addUser(User user) {
        user.setTeam(this);
        users.add(user);
    }
*/
}
