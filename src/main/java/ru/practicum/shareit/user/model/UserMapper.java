package ru.practicum.shareit.user.model;

import ru.practicum.shareit.user.User;

public class UserMapper {

    public static User toUser(UserDto userDto) {
        User user = new User();
        if (userDto.getName() != null) user.setName(userDto.getName());
        user.setId(userDto.getId());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return user;
    }
}
