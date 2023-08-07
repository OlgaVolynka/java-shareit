package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestForItemDtoTest {

    @Test
    void getId() {

        BookingRequestForItemDto booking = new BookingRequestForItemDto();
        booking.setId(1L);
        booking.setBookerId(1L);

        Long id = booking.getId();
        Long bookerId = booking.getBookerId();

        assertEquals(1L, id);
        assertEquals(1L, bookerId);
    }
}