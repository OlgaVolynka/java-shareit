package ru.practicum.shareit.user;

import java.util.List;

public interface UserStorage {

    List<User> findAll();

    User create(User user);

    void updateUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

}