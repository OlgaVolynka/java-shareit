package ru.practicum.shareit.user.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<UserDto> toListUserDto(List<User> users) {
        List<UserDto> userDto = users.stream()
                .map(item -> toUserDto(item))
                .collect(Collectors.toList());

        return userDto;

    }

}
