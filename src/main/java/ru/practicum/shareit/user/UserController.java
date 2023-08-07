package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("Получен запрос GET users");
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Validated({Marker.OnCreate.class}) UserDto user) {
        log.info("Получен запрос POST user");
        return ResponseEntity.ok(userService.create(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Validated({Marker.OnUpdate.class}) UserDto user,
                                              @PathVariable("id") Long userId) {
        log.info("Получен запрос Patch user");
        return ResponseEntity.ok(userService.updateUser(user, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос GET user by id");
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос Delete user by id");
        userService.deleteUserById(userId);
    }
}