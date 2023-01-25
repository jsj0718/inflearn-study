package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

/*
    //다대다
    @ManyToMany(mappedBy = "products")
    private List<User> users = new ArrayList<>();
*/

    //다대다 개선
    @OneToMany(mappedBy = "product")
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
}
