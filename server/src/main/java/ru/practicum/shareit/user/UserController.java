package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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

    private final UserServiceImpl userService;

    @GetMapping
    public List<UserDto> findAll() {
        log.info("Get all users");
        return userService.findAll();
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        log.info("Create user {}", user);
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@RequestBody UserDto user,
                              @PathVariable("id") Long userId) {
        log.info("Update user with id = {}", userId);
        return userService.updateUser(user, userId);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long userId) {
        log.info("Get user with id = {}", userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        log.info("Remove user with id = {}", userId);
        userService.deleteUserById(userId);
    }
}