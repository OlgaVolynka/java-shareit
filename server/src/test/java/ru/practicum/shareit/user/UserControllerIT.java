package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.ErrorHandler;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.user.model.UserDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    private ItemServiceImpl itemService;

    @MockBean
    private ItemRequestController itemRequestController;
    @InjectMocks
    private UserController controller;


    @Test
    void findAll() {
    }

    @SneakyThrows
    @Test
    void create() {

        UserDto userToCreate = new UserDto();
        User user = new User();
        userToCreate.setName("name");
        userToCreate.setEmail("email@email.ru");
        user.setName("name");
        user.setEmail("email@email.ru");
        Mockito.when(userService.create(userToCreate)).thenReturn(userToCreate);
        String result = mockMvc.perform(post("/users")
                        .contentType("application/json")

                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(userToCreate), result);


    }

    @Test
    void saveNewUserWithException() throws Exception {
        UserDto userToCreate = new UserDto();
        Long userId = 0L;

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(ErrorHandler.class)
                .build();

        Mockito.when(userService.create(any()))
                .thenThrow(UnavalibleException.class);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userToCreate))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(400));
    }

    @SneakyThrows
    @Test
    void getUserById() {
        Long userId = 1L;
        mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(userService).getUserById(userId);

    }

    @SneakyThrows
    @Test
    void getUserById_UnavalibleException() {
        Long userId = 1L;
        UnavalibleException e = new UnavalibleException("massage");

        Mockito.when(userService.getUserById(any()))
                .thenThrow(e);

        String result = mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Mockito.verify(userService).getUserById(userId);

    }

    @SneakyThrows
    @Test
    void getUserById_DataNotFoundException() {
        Long userId = 1L;
        DataNotFoundException e = new DataNotFoundException("massage");

        Mockito.when(userService.getUserById(any()))
                .thenThrow(e);

        String result = mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Mockito.verify(userService).getUserById(userId);

    }

    @SneakyThrows
    @Test
    void getUserById_ValidationException() {
        Long userId = 1L;
        ValidationException e = new ValidationException("massage");

        Mockito.when(userService.getUserById(any()))
                .thenThrow(e);

        String result = mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().is(409))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Mockito.verify(userService).getUserById(userId);

    }

    @Test
    void deleteUserById() {
    }
}