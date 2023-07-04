package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.DataAlreadyExist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final InMemoryUserStorage userStorage;

    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    public User create(User user) {

        String email = user.getEmail();
        ArrayList<User> listUser = new ArrayList<>(userStorage.getUsers().values());
        List<String> listEmail = listUser.stream()
                .map(user1 -> user1.getEmail())
                .collect(Collectors.toList());
        if (listEmail.contains(email)) {
            throw new DataAlreadyExist("Данный email уже зарегистрирован");
        }
        return userStorage.create(user);
    }

    public User updateUser(User user, Long userId) {
        User newUser = userStorage.getUserById(userId);
        String truEmail = newUser.getEmail();
        String email = user.getEmail();
        ArrayList<User> listUser = new ArrayList<>(userStorage.getUsers().values());
        List<String> listEmail = listUser.stream()
                .map(user1 -> user1.getEmail())
                .collect(Collectors.toList());

        if (truEmail.equals(email)) {
            userStorage.updateUser(createNewUser(user, newUser, userId));
            return userStorage.getUserById(userId);
        }

        if (listEmail.contains(email)) {
            throw new DataAlreadyExist("Данный email уже зарегистрирован");
        }
        userStorage.updateUser(createNewUser(user, newUser, userId));
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