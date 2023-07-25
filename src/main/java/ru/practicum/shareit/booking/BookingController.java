package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingRequestDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingRequestDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestBody @Validated({Marker.OnCreate.class}) BookingDto booking) {
        log.info("Получен запрос Post booking");
        return bookingService.create(booking, userId);
    }

    @PatchMapping("{bookingId}")
    public Booking updateBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable("bookingId") long bookingId, String approved) {
        log.info("Получен запрос Patch updateBooking");
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("{bookingId}")
    public BookingRequestDto findById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("bookingId") long bookingId) {
        log.info("Получен запрос GET allUsers");
        return bookingService.findById(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<Booking> getOwnerById(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookingsOwner(userId, state);
    }

    @GetMapping
    public List<Booking> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookings(userId, state);
    }
}
