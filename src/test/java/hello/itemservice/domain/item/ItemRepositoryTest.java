package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }
    
    @Test
    @DisplayName("save()와 findById()를 같이 테스트")
    void save() {
        //given
        Item item = new Item("itemA", 1000, 10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 1000, 10);
        Item itemB = new Item("itemB", 2000, 20);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemA, itemB);
    }

    @Test
    void update() {
        //given
        Item item = new Item("itemA", 1000, 10);
        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        //when
        Item newItem = new Item("newItem", 2000, 20);
        itemRepository.update(itemId, newItem);

        Item findItem = itemRepository.findById(itemId);

        //then
        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(newItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(newItem.getQuantity());
    }
}