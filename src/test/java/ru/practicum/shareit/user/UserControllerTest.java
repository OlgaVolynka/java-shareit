package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Test
    void findAll() {
        List<UserDto> listUser = List.of(new UserDto());
        Mockito.when(userService.findAll()).thenReturn(listUser);
        ResponseEntity<List<UserDto>> response = userController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listUser, response.getBody());
        assertNotNull(response);
    }

    @Test
    void create() {
        UserDto newUser = new UserDto();
        Mockito.when(userService.create(new UserDto())).thenReturn(newUser);
        ResponseEntity<UserDto> response = userController.create(new UserDto());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newUser, response.getBody());
        assertNotNull(response);
    }

    @Test
    void updateUser() {

        UserDto newUser = new UserDto();
        newUser.setId(1L);
        Mockito.when(userService.updateUser(new UserDto(), 1L)).thenReturn(newUser);
        ResponseEntity<UserDto> response = userController.updateUser(new UserDto(), 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newUser, response.getBody());
        assertNotNull(response);

    }

    @Test
    void getUserById() {

        UserDto newUser = new UserDto();
        newUser.setId(1L);
        Mockito.when(userService.getUserById(1L)).thenReturn(newUser);
        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newUser, response.getBody());
        assertNotNull(response);

    }

    @Test
    void deleteUserById() {

        UserDto newUser = new UserDto();
        newUser.setId(1L);
        userController.create(newUser);

        userController.getUserById(1L);
        userController.deleteUserById(1L);

    }
}