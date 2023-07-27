package ru.practicum.shareit.item.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {

        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setId(item.getId());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setComments(item.getComments());
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item newItem = new Item();
        if (itemDto.getName() != null) newItem.setName(itemDto.getName());
        if (itemDto.getDescription() != null) newItem.setDescription(itemDto.getDescription());
        if (itemDto.getId() != null) newItem.setId(itemDto.getId());
        if (itemDto.getAvailable() != null) newItem.setAvailable(itemDto.getAvailable());
        return newItem;
    }

    public static List<ItemDto> toListItemDto(List<Item> items) {
        List<ItemDto> itemDto = items.stream()
                .map(item -> toItemDto(item))
                .collect(Collectors.toList());
        return itemDto;
    }
}
