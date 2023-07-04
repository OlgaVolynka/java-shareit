package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController

@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        log.info("Получен запрос GET users");
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос POST user");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long userId) {
        log.info("Получен запрос Patch user");
        return userService.updateUser(user, userId);
    }

    @GetMapping("/{id}")
    public User getUserById(@Valid @PathVariable("id") Long userId) {
        log.info("Получен запрос GET user by id");
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@Valid @PathVariable("id") Long userId) {
        log.info("Получен запрос Delete user by id");
        userService.deleteUserById(userId);
    }
}