package ru.practicum.shareitgateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.Marker;
import ru.practicum.shareitgateway.user.dto.UserDto;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserClient userService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("Get all users");
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated({Marker.OnCreate.class}) UserDto user) {
        log.info("Create user {}", user);
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Validated({Marker.OnUpdate.class}) UserDto user,
                                             @PathVariable("id") Long userId) {
        log.info("Update user with id = {}", userId);
        return userService.updateUser(userId, user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
        log.info("Get user with id = {}", userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Long userId) {
        log.info("Remove user with id = {}", userId);
        return userService.deleteUserById(userId);
    }
}