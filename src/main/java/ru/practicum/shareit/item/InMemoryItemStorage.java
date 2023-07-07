package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Primary
@Qualifier("InMemory")
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();
    private long id = 0;

    public Long countId() {
        return ++id;
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item create(Item item) {

        item.setId(countId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {

        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item getItemById(Long id) {

        if (!items.containsKey(id)) {
            throw new DataNotFoundException("предмет " + id + " не найден");
        }
        return items.get(id);
    }

    @Override
    public void deleteItem(Long itemId) {
        getItemById(itemId);
        items.remove(itemId);
    }
}