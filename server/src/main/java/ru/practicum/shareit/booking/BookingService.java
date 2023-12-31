package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

import java.util.List;


public interface BookingService {
    BookingRequestDto create(BookingDto booking, long userId);

    BookingRequestDto updateStatus(long userId, Long bookingId, String approved);

    BookingRequestDto findById(long userId, long bookingId);

    List<BookingRequestDto> getBookings(Long bookerId, String status, Integer from1, Integer size1);

    List<BookingRequestDto> getBookingsOwner(Long bookerId, String statusInteger, Integer from1, Integer size1);
}
