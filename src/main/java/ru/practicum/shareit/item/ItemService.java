package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private final InMemoryItemStorage itemStorage;
    private final InMemoryUserStorage userStorage;

    public Item getItemById(Long userId) {
        itemStorage.getItemById(userId);
        return itemStorage.getItemById(userId);
    }

    public Item create(ItemDto itemDto, Long userId) {

        Item item = new Item(itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(), userId);
        checkItem(item);

        if (item.getAvailable() == false) {
            throw new ValidationException("не указан статус вещи");
        }
        return itemStorage.create(item);
    }

    public Item updateItem(Item item, Long userId, Long itemId) {

        item.setOwner(userId);
        item.setId(itemId);
        checkItem(item);

        Item oldItem = itemStorage.getItemById(itemId);

        if (item.getOwner() != oldItem.getOwner()) {
            throw new DataNotFoundException("неверно указан id пользователя");
        }
        if (item.getName() != null) oldItem.setName(item.getName());
        if (item.getDescription() != null) oldItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) oldItem.setAvailable(item.getAvailable());

        return itemStorage.updateItem(oldItem);
    }

    public void deleteItemById(Long itemId) {
        itemStorage.deleteItem(itemId);
    }

    public List<Item> findAll(Long userId) {

        List<Item> itemList = itemStorage.findAll();

        return itemList.stream()
                .filter(item -> item.getOwner() == userId)
                .collect(Collectors.toList());
    }

    public List<Item> search(String text) {

        if (text.isBlank()) return new ArrayList<>();
        List<Item> itemList = itemStorage.findAll();
        String lowerCaseText = text.toLowerCase();

        return itemList.stream()
                .filter(item -> (item.getName().toLowerCase().contains(lowerCaseText)
                        || item.getDescription().toLowerCase().contains(lowerCaseText))
                        && item.getAvailable())
                .collect(Collectors.toList());
    }

    private void checkItem(Item item) {
        if (item.getOwner() == null) {
            throw new DataNotFoundException("не указан id пользователя");
        }
        userStorage.getUserById(item.getOwner());
    }
}