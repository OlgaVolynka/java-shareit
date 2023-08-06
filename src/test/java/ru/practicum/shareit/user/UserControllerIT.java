package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.user.model.UserDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private UserService userService;

    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    private ItemServiceImpl itemService;

    @MockBean
    private ItemRequestController itemRequestController;


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

    @SneakyThrows
    @Test
    void updateUser_badRequest() {

        UserDto userToCreate = new UserDto();
        Long userId = 0L;

        userToCreate.setName(null);
        userToCreate.setEmail("null");

        mockMvc.perform(patch("/users/{id}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userService, Mockito.never()).updateUser(userToCreate, userId);

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

    @Test
    void deleteUserById() {
    }
}