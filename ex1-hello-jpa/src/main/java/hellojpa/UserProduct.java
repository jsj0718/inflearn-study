package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserProduct {

    @Id
    @GeneratedValue
    @Column(name = "USER_PRODUCT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;
    private LocalDateTime orderDateTime;

}
