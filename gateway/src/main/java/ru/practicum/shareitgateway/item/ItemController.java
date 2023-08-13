package ru.practicum.shareitgateway.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.Marker;
import ru.practicum.shareitgateway.item.dto.CommentDto;
import ru.practicum.shareitgateway.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemService;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                          @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос GET allUsers");
        return itemService.findAll(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(
            @RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос на список вещей по запросу", text);
        return itemService.search(userId, text, from, size);
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestBody @Validated({Marker.OnCreate.class}) ItemDto itemDto) {
        log.info("Получен запрос Post createItem");
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody @Validated({Marker.OnUpdate.class}) ItemDto item,
                                             @PathVariable("id") long itemId) {
        log.info("Получен запрос Patch updateItem");
        return itemService.updateItem(item, itemId, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @PathVariable("id") Long itemId) {
        log.info("Получен запрос GET item by id");
        return itemService.getItemById(itemId, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete item by id");
        return itemService.deleteItemById(userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> getItemCommentsById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                      @PathVariable("itemId") Long itemId,
                                                      @RequestBody @Valid CommentDto comments) {
        log.info("Получен запрос Post comment");
        return itemService.createItemCommentsById(itemId, userId, comments);
    }
}