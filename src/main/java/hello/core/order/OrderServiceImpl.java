package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드를 매개변수로 받는 생성자를 만들어준다.
public class OrderServiceImpl implements OrderService {

    // 추상화에만 의존 (DIP 성립)
    // final은 처음에 할당되거나, 생성자로 할당이 되어야 한다.
//    @Autowired // 필드 주입은 외부에서 변경할 수 없기 때문에 별도의 setter를 지정해야 한다 -> 차라리 setter에 @Autowired를 거는 것이 낫다.
    private final MemberRepository memberRepository;

//    @Autowired
    @MainDiscountPolicy
    private final DiscountPolicy discountPolicy;

//    @Autowired // applicationContext.getBean(MemberRepository.class)와 거의 비슷한 역할 -> 타입으로만 찾는다.
    // 생성자가 하나만 있으면 @Autowired 생략 가능
    // @Autowired 결과가 2개 이상인 경우, 필드명 또는 파라미터명으로 구분하여 빈 등록을 한다. (fixDiscountPolicy, rateDiscountPolicy 등)
    // @Qualifier("mainDiscountPolicy")로 같은 것을 찾아서 주입
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // setter 주입
//    @Autowired(required = false) // 주입이 없어도 동작하게 만들기 위해 설정할 수 있다.
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }

    // 일반 메서드 주입 (거의 사용 X)
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
