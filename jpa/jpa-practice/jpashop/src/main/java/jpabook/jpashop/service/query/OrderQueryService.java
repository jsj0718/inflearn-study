package jpabook.jpashop.service.query;

import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * API, 화면에 맞춘 Service
 * OSIV 설정을 Off로 바꾼 경우 영속성 컨텍스트가 트랜잭션 종료 시 사라지기 때문에 모든 지연 로딩 관련 작업을 트랜잭션 범위 안에서 끝내야 한다.
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderQueryService {


    private final OrderRepository orderRepository;

    public List<OrderDtoV2> osivOff() {
        return orderRepository.findAllWithItem().stream()
                .map(OrderDtoV2::new)
                .collect(toList());
    }
}
