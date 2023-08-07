package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingRequestDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestBody @Validated({Marker.OnCreate.class}) BookingDto booking) {
        log.info("Получен запрос Post booking");
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingRequestDto updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @PathVariable("bookingId") Long bookingId, String approved) {
        log.info("Получен запрос Patch updateBooking");
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingRequestDto findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable("bookingId") Long bookingId) {
        log.info("Получен запрос GET allUsers");
        return bookingService.findById(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingRequestDto> getOwnerById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestParam(required = false, defaultValue = "ALL") String state,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookingsOwner(userId, state, from, size);
    }

    @GetMapping
    public List<BookingRequestDto> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(defaultValue = "100") @Positive Integer size) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookings(userId, state, from, size);
    }
}
