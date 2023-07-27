package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comments;

import java.util.List;

interface ItemService {
    ItemDto getItemById(long userId, Long itemId);

    ItemDto create(ItemDto itemDto, Long userId);

    ItemDto updateItem(ItemDto itemDto, long userId, long itemId);

    void deleteItemById(Long itemId);

    List<ItemDto> findAll(Long userId);

    List<ItemDto> search(String text);

    Comments getItemCommentsById(Long userId, Long itemId, Comments comments);

}