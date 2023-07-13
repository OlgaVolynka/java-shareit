package ru.practicum.shareit.user.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.User;

@UtilityClass
public final class UserMapper {

    public static User toUser(UserDto userDto) {
        User user = new User();
        if (userDto.getName() != null) user.setName(userDto.getName());
        user.setId(userDto.getId());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return user;
    }
}
