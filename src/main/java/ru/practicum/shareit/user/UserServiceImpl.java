package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new DataNotFoundException("Неверно указан пользователь");
        }
        try {
            User seweUser = userRepository.getReferenceById(userId);
            return UserMapper.toUserDto(seweUser);

        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

    @Override
    public User create(UserDto userDto) {

        try {
            User newUser = UserMapper.toUser(userDto);
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User newUser = UserMapper.toUser(userDto);
        User user = userRepository.getReferenceById(userId);
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

        try {
            User seweUser = userRepository.save(newUser);
            return UserMapper.toUserDto(seweUser);

        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
