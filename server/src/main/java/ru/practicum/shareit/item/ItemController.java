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
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestParam(defaultValue = "0") Integer from,
                                 @RequestParam(defaultValue = "100") Integer size) {
        log.info("Get items for user with id = {} ", userId);
        return itemService.findAll(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "100") Integer size) {
        log.info("Find item with text = {}", text);
        return ResponseEntity.ok(itemService.search(text, from, size));
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemDto itemDto) {
        log.info("Create item for user with id = {} ", userId);
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody ItemDto item,
                              @PathVariable("id") long itemId) {
        log.info("Update item with id = {} for user with id = {} ", itemId, userId);
        return itemService.updateItem(item, userId, itemId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable("id") Long itemId) {
        log.info("Get item with id = {} for user with id = {} ", itemId, userId);
        return itemService.getItemById(userId, itemId);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable("id") Long userId) {
        log.info("Remove item with id = {}", userId);
        itemService.deleteItemById(userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentRequestDto getItemCommentsById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable("itemId") Long itemId,
                                                 @RequestBody CommentDto comments) {
        log.info("Create comment item with id = {}", itemId);
        return itemService.createItemCommentsById(userId, itemId, comments);
    }
}