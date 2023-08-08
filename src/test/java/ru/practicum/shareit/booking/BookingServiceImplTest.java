package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.exeption.BookingTimeException;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {


    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;


    @InjectMocks
    private BookingServiceImpl bookingService;

    LocalDateTime data = LocalDateTime.now();
    User newUser = new User();
    Long userId = 1L;
    User newUser2 = new User();
    Item newItem = new Item();
    BookingDto bookingDto = new BookingDto();
    Booking booking = new Booking();
    Booking booking2 = new Booking();
    Integer from1 = 0;
    Integer size1 = 1;

    @BeforeEach

    private void before() {
        data = LocalDateTime.now();
        newUser = new User();

        userId = 1L;
        newUser.setId(userId);
        newUser.setName("user1");
        newUser.setEmail("user1@email.ru");

        newUser2 = new User();
        newUser2.setId(2L);
        newUser2.setName("user2");
        newUser2.setEmail("user2@email.ru");

        newItem = new Item();
        newItem.setId(0L);
        newItem.setName("name");
        newItem.setDescription("Description");
        newItem.setAvailable(true);
        newItem.setOwner(2L);

        bookingDto = new BookingDto();
        bookingDto.setItemId(0L);
        bookingDto.setStart(data);
        bookingDto.setEnd(data.plusDays(2));
        bookingDto.setId(0L);


        booking = BookingMapper.toBooking(bookingDto);
        booking.setItem(newItem);
        booking.setBooker(newUser);
        booking2.setBooker(newUser2);

    }

    @Test
    void create_whenCorrect() {

        Mockito.when(itemRepository.findById(0L)).thenReturn(Optional.of(newItem));
        Mockito.when(itemRepository.getReferenceById(bookingDto.getItemId())).thenReturn(newItem);


        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(newUser);

        Mockito.when(bookingRepository.save(BookingMapper.toBooking(bookingDto))).thenReturn(booking);

        BookingRequestDto expectedBookingDto = bookingService.create(bookingDto, userId);

        assertEquals(BookingMapper.toBookingReqwDto(booking), expectedBookingDto);


    }

    @Test
    void create_whenNotFound() {

        bookingDto.setEnd(data.minusDays(2));

        final BookingTimeException exception = assertThrows(BookingTimeException.class, () -> bookingService.create(bookingDto, userId));
        assertEquals(exception.getMessage(), "Некорректно указаны даты бронирования");
    }

    @Test
    void create_whenNotFoundItem() {

        Mockito.when(itemRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> bookingService.create(bookingDto, userId));

    }

    @Test
    void create_whenNotAvailableItem() {

        newItem.setAvailable(false);

        Mockito.when(itemRepository.findById(0L)).thenReturn(Optional.of(newItem));
        Mockito.when(itemRepository.getReferenceById(bookingDto.getItemId())).thenReturn(newItem);

        assertThrows(UnavalibleException.class, () -> bookingService.create(bookingDto, userId));

    }


    @Test
    void updateStatus() {
        userId = 2L;
        booking.setStatus(Status.WAITING);
        booking2.setStatus(Status.APPROVED);
        booking2.setId(0L);
        booking2.setStart(data);
        booking2.setEnd(booking.getEnd());
        booking2.setItem(newItem);
        booking2.setBooker(newUser);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
        Mockito.when(bookingRepository.getReferenceById(bookingDto.getId())).thenReturn(booking);

        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        BookingRequestDto expectedBookingDto = bookingService.updateStatus(userId, bookingDto.getId(), "true");

        assertEquals(BookingMapper.toBookingReqwDto(booking2), expectedBookingDto);

    }

    @Test
    void updateStatus_byWrongStatus() {
        userId = 2L;
        booking.setStatus(Status.APPROVED);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
        Mockito.when(bookingRepository.getReferenceById(bookingDto.getId())).thenReturn(booking);
        assertThrows(UnavalibleException.class, () -> bookingService.updateStatus(userId, bookingDto.getId(), "true"));

    }

    @Test
    void getBookings_statusREJECTED() {

        Long bookerId = userId;
        String status = "REJECTED";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.REJECTED, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookings_withoutUserId() {

        Long bookerId = null;
        String status = "REJECTED";

        assertThrows(NullPointerException.class, () -> bookingService.getBookings(bookerId, status, from1, size1), "true");
    }

    @Test
    void getBookings_withWrongUserId() {

        Long bookerId = 6L;
        String status = "REJECTED";

        Mockito.when(userRepository.findById(bookerId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> bookingService.getBookings(bookerId, status, from1, size1), "true");

    }

    @Test
    void getBookings_statusALL() {

        Long bookerId = userId;
        String status = "ALL";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findByBookerId(bookerId, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookingsOwner() {

        Long bookerId = userId;
        String status = "ALL";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_Owner(bookerId, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void findById() {

        long bookingId = 1;
        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));


        Mockito.when(bookingRepository.getReferenceById(bookingId)).thenReturn(booking);
        BookingRequestDto expectedBookingDto = bookingService.findById(userId, bookingId);
        assertEquals(BookingMapper.toBookingReqwDto(booking), expectedBookingDto);

    }

    @Test
    void findById_notFoundBooking() {

        long bookingId = 1;

        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> bookingService.findById(userId, bookingId));

    }

    @Test
    void findById_noAccess() {

        long bookingId = 1;
        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        booking.setBooker(newUser2);
        Mockito.when(bookingRepository.getReferenceById(bookingId)).thenReturn(booking);

        assertThrows(DataNotFoundException.class, () -> bookingService.findById(userId, bookingId));

    }

    @Test
    void getBookingsOwner_statusCURRENT() {

        Long bookerId = userId;
        String status = "CURRENT";


        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_OwnerAndEndAfterAndStartBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void getBookingsOwner_statusPAST() {

        Long bookerId = userId;
        String status = "PAST";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_OwnerAndEndBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void getBookingsOwner_statusFUTURE() {

        Long bookerId = userId;
        String status = "FUTURE";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_OwnerAndStartAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void getBookingsOwner_statusWAITING() {

        Long bookerId = userId;
        String status = "WAITING";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.WAITING, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void getBookingsOwner_statusREJECTED() {

        Long bookerId = userId;
        String status = "REJECTED";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.REJECTED, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookingsOwner(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);

    }

    @Test
    void getBookingsOwner_statusDefault() {

        Long bookerId = userId;
        String status = "default";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        assertThrows(UnavalibleException.class, () -> bookingService.getBookingsOwner(bookerId, status, from1, size1));

    }

    @Test
    void getBookings_statusCURRENT() {

        Long bookerId = userId;
        String status = "CURRENT";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByBooker_IdAndEndAfterAndStartBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookings_statusPAST() {

        Long bookerId = userId;
        String status = "PAST";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByBooker_IdAndEndBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookings_statusFUTURE() {

        Long bookerId = userId;
        String status = "FUTURE";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        Mockito.when(bookingRepository.findAllByBooker_IdAndStartAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookings_statusWAITING() {

        Long bookerId = userId;
        String status = "WAITING";

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));


        Mockito.when(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.WAITING, page)).thenReturn(List.of(booking));
        List<BookingRequestDto> expectedBookingDto = bookingService.getBookings(bookerId, status, from1, size1);
        assertEquals(BookingMapper.toListBookingDto(List.of(booking)), expectedBookingDto);
    }

    @Test
    void getBookings_statusDefault() {

        Long bookerId = userId;
        String status = "default";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));

        assertThrows(UnavalibleException.class, () -> bookingService.getBookings(bookerId, status, from1, size1));
    }
}