package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new DataNotFoundException("Неверно указан пользователь");
        }

        User seweUser = userRepository.getReferenceById(userId);
        return UserMapper.toUserDto(seweUser);
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {

        try {
            User newUser = UserMapper.toUser(userDto);
            return UserMapper.toUserDto(userRepository.save(newUser));
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User newUser = UserMapper.toUser(userDto);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User with id=" + newUser.getId() + " not found");
        }
        User oldUser = userRepository.getReferenceById(userId);
        if (newUser.getName() == null) {
            newUser.setName(oldUser.getName());
        }
        if (newUser.getEmail() == null) {
            newUser.setEmail(oldUser.getEmail());
        }
        newUser.setId(userId);

        try {
            User seweUser = userRepository.save(newUser);
            return UserMapper.toUserDto(seweUser);

        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> findAll() {
        return UserMapper.toListUserDto(userRepository.findAll());
    }
}
