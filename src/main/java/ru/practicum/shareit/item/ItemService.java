package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

interface ItemService {
    ItemDto getItemById(long userId, Long itemId);

    ItemDto create(ItemDto itemDto, Long userId);

    ItemDto updateItem(ItemDto itemDto, long userId, long itemId);

    void deleteItemById(Long itemId);

    List<ItemDto> findAll(Long userId);

    List<ItemDto> search(String text);

    CommentRequestDto createItemCommentsById(Long userId, Long itemId, CommentDto comments);

}