package ru.practicum.shareitgateway.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.Marker;
import ru.practicum.shareitgateway.booking.dto.BookingDto;


import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


/**
 * TODO Sprint add-bookings.
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {

    private final BookingClient bookingService;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestBody @Validated({Marker.OnCreate.class}) BookingDto booking) {
        log.info("Creating a booking {}, userId={}", booking, userId);
        return bookingService.create(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @PathVariable("bookingId") Long bookingId, Boolean approved) {
        log.info("Approve booking with id =  {}", bookingId);
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @PathVariable("bookingId") Long bookingId) {
        log.info("Get booking {} of userId={}", bookingId, userId);
        return bookingService.findById(userId, bookingId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getOwnerById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "100") @Positive Integer size) {

        BookingState bookingState = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect state: " + stateParam));


        log.info("Get booking with status {}, userId={}, from={}, size={}", bookingState, userId, from, size);


        return bookingService.getBookingsOwner(userId, bookingState, from, size);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "100") @Positive Integer size) {

        BookingState bookingState = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect state: " + stateParam));

        log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);

        return bookingService.getBookings(userId, bookingState, from, size);
    }
}
