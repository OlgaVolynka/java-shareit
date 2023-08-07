package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                 @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос GET allUsers");
        return itemService.findAll(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос на список вещей по запросу", text);
        return ResponseEntity.ok(itemService.search(text, from, size));
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody @Validated({Marker.OnCreate.class}) ItemDto itemDto) {
        log.info("Получен запрос Post createItem");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody @Validated({Marker.OnUpdate.class}) ItemDto item,
                              @PathVariable("id") long itemId) {
        log.info("Получен запрос Patch updateItem");
        return itemService.updateItem(item, userId, itemId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable("id") Long itemId) {
        log.info("Получен запрос GET item by id");
        return itemService.getItemById(userId, itemId);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete item by id");
        itemService.deleteItemById(userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentRequestDto getItemCommentsById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable("itemId") Long itemId,
                                                 @RequestBody @Valid CommentDto comments) {
        log.info("Получен запрос Post comment");
        return itemService.createItemCommentsById(userId, itemId, comments);
    }
}