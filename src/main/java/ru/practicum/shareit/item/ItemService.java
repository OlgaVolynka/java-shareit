package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.exeption.WithoutXSharerUserId;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("InMemory")
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public Item getItemById(Long userId) {
        itemStorage.getItemById(userId);
        return itemStorage.getItemById(userId);
    }

    public Item create(ItemDto itemDto, Long userId) {

        if (userId == null) {
            throw new WithoutXSharerUserId("Не указан id пользователя");
        }

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        checkItem(item);

        if (item.getAvailable() == false) {
            throw new ValidationException("не указан статус вещи");
        }
        return itemStorage.create(item);
    }

    public Item updateItem(ItemDto itemDto, Long userId, long itemId) {
        if (userId == null) {
            throw new WithoutXSharerUserId("Не указан id пользователя");
        }

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        item.setId(itemId);
        checkItem(item);

        Item oldItem = itemStorage.getItemById(itemId);

        if (userId != oldItem.getOwner()) {
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

    public List<Item> findAll(long userId) {

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