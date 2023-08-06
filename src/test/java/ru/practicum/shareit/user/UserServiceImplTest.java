package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserById_whenUserFound() {
        Long userId = 0L;
        User expectidUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectidUser));
        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(expectidUser);
        UserDto userDto = userService.getUserById(userId);

        assertEquals(expectidUser, UserMapper.toUser(userDto));
    }

    @Test
    void getUserById_whenUserNotFound() {
        Long userId = 0L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUserById_UserNotCorrect() {
        Long userId = 0L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void create_whenUserCorrect() {

        User expectidUser = new User();
        Mockito.when(userRepository.save(expectidUser)).thenReturn(expectidUser);
        UserDto userDto = userService.create(UserMapper.toUserDto(expectidUser));

        assertEquals(expectidUser, UserMapper.toUser(userDto));

    }

    @Test
    void create_whenUserNotCorrect() {

        User expectidUser = new User();
        Mockito.doThrow(DataIntegrityViolationException.class)
                .when(userRepository).save(expectidUser);
        assertThrows(ValidationException.class, () -> userService.create(UserMapper.toUserDto(expectidUser)));

    }

    @Test
    void updateUser_whenUserFound() {

        Long userId = 0L;
        User expectidUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectidUser));

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(expectidUser);
        Mockito.when(userRepository.save(expectidUser)).thenReturn(expectidUser);
        UserDto userDto = userService.updateUser(UserMapper.toUserDto(expectidUser), userId);

        assertEquals(expectidUser, UserMapper.toUser(userDto));

    }

    @Test
    void updateUser_whenUserNotFound() {

        Long userId = 0L;
        User expectidUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userService.updateUser(UserMapper.toUserDto(expectidUser), userId));

    }

    @Test
    void updateUser_whenUserNotCorrect() {

        Long userId = 0L;
        User expectidUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectidUser));


        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(expectidUser);

        Mockito.doThrow(DataIntegrityViolationException.class)
                .when(userRepository).save(expectidUser);
        assertThrows(ValidationException.class, () -> userService.updateUser(UserMapper.toUserDto(expectidUser), userId));

    }

    @Test
    void deleteUserById() {

        Long userId = 0L;
        userService.deleteUserById(userId);

        Mockito.verify(userRepository).deleteById(userId);

    }

    @Test
    void findAll() {

        List<User> expectidUser = List.of(new User());
        Mockito.when(userRepository.findAll()).thenReturn(expectidUser);
        List<UserDto> userDto = userService.findAll();

        assertEquals(UserMapper.toListUserDto(expectidUser), userDto);

    }
}