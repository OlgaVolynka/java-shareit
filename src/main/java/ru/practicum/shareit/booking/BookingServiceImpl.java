package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.exeption.BookingTimeException;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public BookingRequestDto create(BookingDto bookingDto, long userId) {
        checkDate(bookingDto);
        Booking booking = BookingMapper.toBooking(bookingDto);

        itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new DataNotFoundException("Не найден id предмета"));

        if (!itemRepository.getReferenceById(bookingDto.getItemId()).getAvailable()) {
            throw new UnavalibleException("Товар уже занят");
        }

        checkUser(userId);
        booking.setStatus(Status.WAITING);
        booking.setBooker(userRepository.getReferenceById(userId));
        booking.setItem(itemRepository.getReferenceById(bookingDto.getItemId()));

        if (booking.getItem().getOwner() == userId) {
            throw new DataNotFoundException("Нельзя забронировать свою же вещь");
        }
        return BookingMapper.toBookingReqwDto(bookingRepository.save(booking));

    }

    @Transactional
    @Override
    public BookingRequestDto updateStatus(long userId, Long bookingId, String approved) {


        checkUser(userId);
        if (bookingRepository.getReferenceById(bookingId).getBooker().getId() == userId) {
            throw new DataNotFoundException("нет доступа для подтверждения брони");
        }
        if (bookingRepository.getReferenceById(bookingId).getItem().getOwner() != userId) {
            throw new UnavalibleException("нет доступа для подтверждения брони");
        }
        Booking booking = bookingRepository.getReferenceById(bookingId);


        if (booking.getStatus() != Status.WAITING) {
            throw new UnavalibleException("Статус вещи не в статусе WAITING");
        }

        if (booking.getBooker().getId() != bookingRepository.getReferenceById(bookingId).getBooker().getId()) {
            throw new DataNotFoundException("неверно указан пользователь вещи");
        }

        booking.setId(bookingId);
        booking.setBooker(bookingRepository.getReferenceById(bookingId).getBooker());
        if (approved.equals("true")) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }

        return BookingMapper.toBookingReqwDto(bookingRepository.save(booking));
    }


    @Override
    public List<BookingRequestDto> getBookings(Long bookerId, String status, Integer from1, Integer size1) {
        checkUser(bookerId);

        Sort sort = Sort.by("start").descending();
        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        switch (status) {
            case "ALL":
                return BookingMapper.toListBookingDto(bookingRepository.findByBookerId(bookerId, page));
            case "CURRENT":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndEndAfterAndStartBefore(bookerId, LocalDateTime.now(), LocalDateTime.now(), page));
            case "PAST":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndEndBefore(bookerId, LocalDateTime.now(), page));
            case "FUTURE":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStartAfter(bookerId, LocalDateTime.now(), page));
            case "WAITING":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.WAITING, page));
            case "REJECTED":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.REJECTED, page));
            default:
                throw new UnavalibleException("Unknown state: " + status);
        }
    }


    @Override
    public List<BookingRequestDto> getBookingsOwner(Long bookerId, String status, Integer from1, Integer size1) {
        checkUser(bookerId);

        Sort sort = Sort.by("start").descending();

        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        switch (status) {
            case "ALL":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_Owner(bookerId, page));
            case "CURRENT":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndEndAfterAndStartBefore(bookerId, LocalDateTime.now(), LocalDateTime.now(), page));
            case "PAST":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndEndBefore(bookerId, LocalDateTime.now(), page));
            case "FUTURE":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStartAfter(bookerId, LocalDateTime.now(), page));
            case "WAITING":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.WAITING, page));
            case "REJECTED":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.REJECTED, page));
            default:
                throw new UnavalibleException("Unknown state: " + status);
        }
    }

    private void checkUser(long userId) {

        userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Не найден пользователь по id=" + userId));

    }

    private void checkDate(BookingDto bookingDto) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new BookingTimeException("Некорректно указаны даты бронирования");
        }
    }

    @Override
    public BookingRequestDto findById(long userId, long bookingId) {


        if (bookingRepository.findById(bookingId).isEmpty()) {
            throw new DataNotFoundException("Не найден id бронирования");
        }

        if (userId != bookingRepository.getReferenceById(bookingId).getItem().getOwner() &&
                userId != bookingRepository.getReferenceById(bookingId).getBooker().getId()) {
            throw new DataNotFoundException("У вас нет доступа к этим данным");
        }

        Booking newBooking = bookingRepository.getReferenceById(bookingId);
        return BookingMapper.toBookingReqwDto(newBooking);
    }
}
