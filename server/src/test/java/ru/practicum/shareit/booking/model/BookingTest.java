package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingTest {

    @Test
    void testEquals() {

        Booking booking = new Booking();
        Booking booking1 = new Booking();
        booking1.setId(1L);

        Item item = new Item();
        User booker = new User();
        LocalDateTime data = LocalDateTime.now();
        LocalDateTime dataEnd = LocalDateTime.now().plusHours(7);

        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(Status.APPROVED);
        booking.setId(1L);
        booking.setStart(data);
        booking.setEnd(dataEnd);

        Item item1 = booking.getItem();
        User booker1 = booking.getBooker();
        Status status = booking.getStatus();
        Long id = booking.getId();
        LocalDateTime data1 = booking.getStart();
        LocalDateTime dataEnd1 = booking.getEnd();


        assertEquals(item, item1);
        assertEquals(booker, booker1);
        assertEquals(Status.APPROVED, status);
        assertEquals(1L, id);
        assertEquals(data, data1);
        assertEquals(dataEnd, dataEnd1);
        assertEquals(booking, booking1);

    }
}