package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

interface UserService {
    UserDto getUserById(Long userId);

    User create(UserDto userDto);

    UserDto updateUser(UserDto userDto, Long userId);

    void deleteUserById(Long userId);

    List<User> findAll();
}