package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public BookingRequestDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestBody BookingDto booking) {
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
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "100") Integer size) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookingsOwner(userId, state, from, size);
    }

    @GetMapping
    public List<BookingRequestDto> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "100") Integer size) {
        log.info("Получен запрос GET allUsers");
        return bookingService.getBookings(userId, state, from, size);
    }
}
