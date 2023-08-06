package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoTest {

    @Test
    void getId() {

        LocalDateTime data = LocalDateTime.now();
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(1L);
        bookingDto.setStatus(Status.APPROVED);
        bookingDto.setEnd(data);
        bookingDto.setStart(data);
        bookingDto.setItemId(1L);

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