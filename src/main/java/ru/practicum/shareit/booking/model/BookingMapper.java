package ru.practicum.shareit.booking.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setId(booking.getId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setItemId(booking.getItem().getId());
       // bookingDto.setBooker(booking.getBooker());
        return bookingDto;
    }

    public static Booking toBooking(BookingDto bookingDto) {
        Booking newBooking = new Booking();
        //if (bookingDto.getStatus() != null) newBooking.setStatus(bookingDto.getStatus());
       // if (bookingDto.getItemId() != null) newBooking.setItemId(bookingDto.getItemId().);
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
        bookingReqwDto.setItem(booking.getItem());
        bookingReqwDto.setBooker(booking.getBooker());
        return bookingReqwDto;
    }

}
