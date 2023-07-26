package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingRequestDto;

import java.util.List;

public interface BookingService {
    BookingRequestDto create(BookingDto booking, long userId);

    Booking updateStatus(long userId, Long bookingId, String approved);

    BookingRequestDto findById(long userId, long bookingId);

    List<Booking> getBookings(Long bookerId, String status);

    List<Booking> getBookingsOwner(Long bookerId, String status);
}
