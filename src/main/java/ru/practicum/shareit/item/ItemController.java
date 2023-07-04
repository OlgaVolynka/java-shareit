package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping(path = "/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> findAll(@RequestHeader("X-Sharer-User-Id") Long userI) {
        log.info("Получен запрос GET users");
        return itemService.findAll(userI);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String text) {
        log.info("Получен запрос на список вещей по запросу", text);
        return itemService.search(text);
    }

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("{id}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody Item item, @PathVariable("id") Long itemId) {
        log.info("Получен запрос PUT item");
        return itemService.updateItem(item, userId, itemId);
    }

    @GetMapping("{id}")
    public Item getItemById(@PathVariable("id") Long userId) {
        log.info("Получен запрос GET item by id");
        return itemService.getItemById(userId);
    }

    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete item by id");
        itemService.deleteItemById(userId);
    }
}