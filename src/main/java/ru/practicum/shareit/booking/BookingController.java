package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

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
    public ResponseEntity<BookingRequestDto> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                           @RequestBody @Validated({Marker.OnCreate.class}) BookingDto booking) {
        log.info("Получен запрос Post booking");
        return ResponseEntity.ok(bookingService.create(booking, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingRequestDto> updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                           @PathVariable("bookingId") Long bookingId, String approved) {
        log.info("Получен запрос Patch updateBooking");
        return ResponseEntity.ok(bookingService.updateStatus(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingRequestDto> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                      @PathVariable("bookingId") Long bookingId) {
        log.info("Получен запрос GET allUsers");
        return ResponseEntity.ok(bookingService.findById(userId, bookingId));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingRequestDto>> getOwnerById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                                @RequestParam(required = false, defaultValue = "ALL") String state,
                                                                @RequestParam(required = false) Integer from,
                                                                @RequestParam(required = false) Integer size) {
        log.info("Получен запрос GET allUsers");
        return ResponseEntity.ok(bookingService.getBookingsOwner(userId, state, from, size));
    }

    @GetMapping
    public ResponseEntity<List<BookingRequestDto>> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                                  @RequestParam(defaultValue = "ALL") String state,
                                                                  @RequestParam(required = false) Integer from,
                                                                  @RequestParam(required = false) Integer size) {
        log.info("Получен запрос GET allUsers");
        return ResponseEntity.ok(bookingService.getBookings(userId, state, from, size));
    }
}
