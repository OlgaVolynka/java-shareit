package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private BookingRequestDto bookingRequestDto = new BookingRequestDto();
    private BookingDto bookingDto = new BookingDto();

    @BeforeEach
    public void before() {
        bookingDto.setItemId(1L);
        bookingDto.setId(1L);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(7));
        bookingDto.setStatus(Status.APPROVED);
    }

    @Test
    void createBooking() {

        Mockito.when(bookingService.create(bookingDto, 1L)).thenReturn(bookingRequestDto);
        BookingRequestDto response = bookingController.createBooking(1L, bookingDto);
        assertEquals(bookingRequestDto, response);
    }

    @Test
    void updateBooking() {

        Mockito.when(bookingService.updateStatus(1L, 1L, "text")).thenReturn(bookingRequestDto);
        BookingRequestDto response = bookingController.updateBooking(1L, 1L, "text");
        assertEquals(bookingRequestDto, response);

    }

    @Test
    void findById() {

        Mockito.when(bookingService.findById(1L, 1L)).thenReturn(bookingRequestDto);
        BookingRequestDto response = bookingController.findById(1L, 1L);
        assertEquals(bookingRequestDto, response);

    }

    @Test
    void getOwnerById() {

        List<BookingRequestDto> list = List.of(bookingRequestDto);
        Mockito.when(bookingService.getBookingsOwner(1L, "text", 0, 1)).thenReturn(list);
        List<BookingRequestDto> response = bookingController.getOwnerById(1L, "text", 0, 1);
        assertEquals(list, response);

    }

    @Test
    void getBookingById() {

        List<BookingRequestDto> list = List.of(bookingRequestDto);
        Mockito.when(bookingService.getBookings(1L, "text", 0, 1)).thenReturn(list);
        List<BookingRequestDto> response = bookingController.getBookingById(1L, "text", 0, 1);
        assertEquals(list, response);
    }
}