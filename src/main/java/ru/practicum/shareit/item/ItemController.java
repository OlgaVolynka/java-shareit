package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comments;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос GET allUsers");
        return itemService.findAll(userId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String text) {
        log.info("Получен запрос на список вещей по запросу", text);
        return itemService.search(text);
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody @Validated({Marker.OnCreate.class}) ItemDto itemDto) {
        log.info("Получен запрос Post createItem");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("{id}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @RequestBody @Validated({Marker.OnUpdate.class}) ItemDto item, @PathVariable("id") long itemId) {
        log.info("Получен запрос Patch updateItem");
        return itemService.updateItem(item, userId, itemId);
    }

    @GetMapping("{id}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long itemId) {
        log.info("Получен запрос GET item by id");
        return itemService.getItemById(userId, itemId);
    }

    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete item by id");
        itemService.deleteItemById(userId);
    }

    @PostMapping("/{itemId}/comment")
    public Comments getItemCommentsById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("itemId") Long itemId, @RequestBody @Valid Comments comments) {
        log.info("Получен запрос Past comment");
        return itemService.getItemCommentsById(userId, itemId, comments);
    }
}