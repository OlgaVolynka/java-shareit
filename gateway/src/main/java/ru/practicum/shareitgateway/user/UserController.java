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
        log.info("Получен запрос GET users");
        return userService.findAll();
    }

    @PostMapping
    public Object create(@RequestBody @Validated({Marker.OnCreate.class}) UserDto user) {
        log.info("Получен запрос POST user");
        return userService.create(user).getBody();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Validated({Marker.OnUpdate.class}) UserDto user,
                                             @PathVariable("id") Long userId) {
        log.info("Получен запрос Patch user");
        return userService.updateUser(userId, user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос GET user by id");
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete user by id");
        return userService.deleteUserById(userId);
    }
}