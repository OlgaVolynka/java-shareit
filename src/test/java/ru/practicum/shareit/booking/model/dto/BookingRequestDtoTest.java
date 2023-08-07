package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestDtoTest {

    @Test
    void getId() {

        LocalDateTime data = LocalDateTime.now();
        BookingRequestDto bookingDto = new BookingRequestDto();

        bookingDto.setId(1L);
        bookingDto.setStatus(Status.APPROVED);
        bookingDto.setEnd(data);
        bookingDto.setStart(data);
        bookingDto.setBooker(new User());
        bookingDto.setItem(new ItemDto());

        Long id = bookingDto.getId();
        Status status = bookingDto.getStatus();
        LocalDateTime data1 = bookingDto.getEnd();
        LocalDateTime data2 = bookingDto.getStart();
        Long itemId = bookingDto.getId();

        assertEquals(1L, id);
        assertEquals(Status.APPROVED, status);
        assertEquals(data, data1);
        assertEquals(data, data2);
        assertEquals(1L, itemId);


    }
}