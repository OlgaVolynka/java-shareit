package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingRequestDto;

import java.util.List;

public interface BookingService {
    BookingRequestDto create(BookingDto booking, Long userId);
    Booking updateStatus(Long userId, Long bookingId, String approved);
    BookingRequestDto findById(Long userId, Long bookingId);
    List<Booking> getBookings (Long BookerId, String status);
    List<Booking> getBookingsOwner (Long bookerId, String status);
}
