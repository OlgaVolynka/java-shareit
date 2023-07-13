package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeption.DataAlreadyExist;
import ru.practicum.shareit.exeption.DataNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Primary
@Qualifier("bd")
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    public Long countId() {
        return ++id;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        String email = user.getEmail();
        ArrayList<User> listUser = new ArrayList<>(users.values());
        List<String> listEmail = listUser.stream()
                .map(user1 -> user1.getEmail())
                .collect(Collectors.toList());
        if (listEmail.contains(email)) {
            throw new DataAlreadyExist("Данный email:" + email + " уже зарегистрирован");
        }
        user.setId(countId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void updateUser(User user) {

        String email = user.getEmail();

        ArrayList<User> listUser = new ArrayList<>(users.values());
        List<String> listEmail = listUser.stream()
                .filter(user1 -> user1.getId() != user.getId())
                .map(user1 -> user1.getEmail())
                .collect(Collectors.toList());
        if (email != null && listEmail != null && listEmail.contains(email)) {
            throw new DataAlreadyExist("Данный email:" + email + " уже зарегистрирован");
        }
        users.put(user.getId(), user);
    }

    public User getUserById(Long id) {

        if (!users.containsKey(id)) {
            throw new DataNotFoundException("пользователь " + id + " не найден");
        }
        return users.get(id);
    }

    @Override
    public void deleteUser(Long userId) {
        getUserById(userId);
        users.remove(userId);
    }
}