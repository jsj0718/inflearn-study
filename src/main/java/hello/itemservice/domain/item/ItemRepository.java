package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // Spring Container 안에서 싱글톤이기에 static을 사용하지 않아도 된다.
    // 따로 new로 생성하는 경우 static을 안하면 새로 생성되기 때문에 방지하고자 사용
    // 멀티 쓰레드 환경에서 HashMap에 접근하면 동시성 문제로 사용하면 안된다. (싱글톤이기 때문에 독립적이지 못하다.)
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); // 비파괴적 방법
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
