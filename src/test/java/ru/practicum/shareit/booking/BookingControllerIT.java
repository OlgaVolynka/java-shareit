package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.exeption.BookingTimeException;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.model.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookingControllerIT {
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


    @Test
    @SneakyThrows
    void createBooking() {

        BookingDto bookingDto = new BookingDto();
        LocalDateTime dateTime = LocalDateTime.now();
        userService.create(new UserDto());

        Item item = new Item();
        item.setName("name");
        itemService.create(new ItemDto(), 1L);

        bookingDto.setStatus(Status.APPROVED);
        bookingDto.setStart(dateTime.plusHours(5));
        bookingDto.setEnd(dateTime.plusHours(7));
        bookingDto.setItemId(1L);

        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setItem(item);

        BookingRequestDto bookingRequestDto = BookingMapper.toBookingReqwDto(booking);

        Long userId = 1L;

        Mockito.when(bookingService.create(bookingDto, userId)).thenReturn(bookingRequestDto);
        String result = mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertEquals(objectMapper.writeValueAsString(BookingMapper.toBookingReqwDto(booking)), result);

    }

    @SneakyThrows
    @Test
    void getUserById_BookingTimeException() {
        Long userId = 1L;
        BookingTimeException e = new BookingTimeException("massage");

        Mockito.when(bookingService.findById(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(e);

        mockMvc.perform(get("/bookings/{bookingId}", userId).header("X-Sharer-User-Id", userId))

                .andDo(print())
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

}