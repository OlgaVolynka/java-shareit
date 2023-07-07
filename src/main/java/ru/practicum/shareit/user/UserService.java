package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Qualifier("InMemory")
    private final UserStorage userStorage;

    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    public User create(UserDto userDto) {

        User newUser = UserMapper.toUser(userDto);
        return userStorage.create(newUser);
    }

    public User updateUser(UserDto userDto, Long userId) {

        User newUser = UserMapper.toUser(userDto);

        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new DataNotFoundException("User with id=" + newUser.getId() + " not found");
        }

        if (newUser.getName() == null) {
            newUser.setName(user.getName());
        }
        if (newUser.getEmail() == null) {
            newUser.setEmail(user.getEmail());
        }
        newUser.setId(userId);
        userStorage.updateUser(newUser);


        return userStorage.getUserById(userId);
    }

    public void deleteUserById(Long userId) {
        userStorage.deleteUser(userId);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    private User createNewUser(User user, User newUser, Long userId) {
        newUser.setId(userId);
        if (user.getEmail() != null) newUser.setEmail(user.getEmail());
        if (user.getName() != null) newUser.setName(user.getName());
        return newUser;
    }
}