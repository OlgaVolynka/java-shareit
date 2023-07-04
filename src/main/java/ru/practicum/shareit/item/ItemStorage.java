package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    List<Item> findAll();

    Item create(Item item);

    Item updateItem(Item item);

    Item getItemById(Long id);

    void deleteItem(Long id);

}