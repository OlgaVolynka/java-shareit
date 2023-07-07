package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController

@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        log.info("Получен запрос GET users");
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody @Validated({Marker.OnCreate.class}) UserDto user) {
        log.info("Получен запрос POST user");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestBody UserDto user, @PathVariable("id") Long userId) {
        log.info("Получен запрос Patch user");
        return userService.updateUser(user, userId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос GET user by id");
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete user by id");
        userService.deleteUserById(userId);
    }
}