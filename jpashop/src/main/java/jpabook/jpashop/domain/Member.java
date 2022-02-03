package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty //엔티티에는 검증 기능 X -> 점점 복잡해지기 시작하는 원흉
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore //json 반환에 제외 -> 눈 앞의 문제만 해결할 뿐 복잡한 상황에서는 해결할 수 없다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
