package ru.practicum.shareit.item.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;

@UtilityClass
public final class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(itemDto.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setId(item.getId());
        itemDto.setAvailable(item.getAvailable());
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
}
