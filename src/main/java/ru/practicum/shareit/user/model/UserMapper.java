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

    public static UserDto toUserDto(User newUser) {
        UserDto newUser2 = new UserDto();
        newUser2.setId(newUser.getId());
        newUser2.setName(newUser.getName());
        newUser2.setEmail(newUser.getEmail());
        return newUser2;
    }
}
