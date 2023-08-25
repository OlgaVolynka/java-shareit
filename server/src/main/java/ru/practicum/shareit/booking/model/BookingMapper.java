package ru.practicum.shareit.booking.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.item.model.ItemMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookingMapper {

    public static Booking toBooking(BookingDto bookingDto) {
        Booking newBooking = new Booking();
        if (bookingDto.getEnd() != null) newBooking.setEnd(bookingDto.getEnd());
        if (bookingDto.getStart() != null) newBooking.setStart(bookingDto.getStart());
        return newBooking;
    }

    public static BookingRequestDto toBookingReqwDto(Booking booking) {
        BookingRequestDto bookingReqwDto = new BookingRequestDto();
        bookingReqwDto.setStart(booking.getStart());
        bookingReqwDto.setEnd(booking.getEnd());
        bookingReqwDto.setId(booking.getId());
        bookingReqwDto.setStatus(booking.getStatus());
        bookingReqwDto.setItem(ItemMapper.toItemDto(booking.getItem()));
        bookingReqwDto.setBooker(booking.getBooker());
        return bookingReqwDto;
    }

    public static List<BookingRequestDto> toListBookingDto(List<Booking> bookings) {
        List<BookingRequestDto> bookingRequestDtos = bookings.stream()
                .map(booking -> toBookingReqwDto(booking))
                .collect(Collectors.toList());

        return bookingRequestDtos;
    }
}
