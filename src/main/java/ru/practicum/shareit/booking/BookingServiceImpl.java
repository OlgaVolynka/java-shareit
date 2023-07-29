package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.exeption.BookingTimeException;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;


import java.time.LocalDateTime;
import java.util.Collections;
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

        if (itemRepository.findAllById(Collections.singleton(bookingDto.getItemId())).isEmpty()) {
            throw new DataNotFoundException("Не найден id предмета");
        }

        if (!itemRepository.getReferenceById(bookingDto.getItemId()).getAvailable()) {
            throw new UnavalibleException("Товар уже занят");
        }

        checkUser(userId);
        booking.setStatus(Status.WAITING);
        booking.setBooker(userRepository.getReferenceById(userId));
        booking.setItem(itemRepository.getOne(bookingDto.getItemId()));

        if (booking.getItem().getOwner() == userId) {
            throw new DataNotFoundException("Нельзя забронировать свою же вещь");
        }

        Booking newBooking = bookingRepository.save(booking);
        return BookingMapper.toBookingReqwDto(newBooking);

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
    public List<BookingRequestDto> getBookings(Long bookerId, String status) {
        checkUser(bookerId);

        Sort sort = Sort.by("start").descending();

        switch (status) {
            case "ALL":
                return BookingMapper.toListBookingDto(bookingRepository.findByBookerId(bookerId, sort));

            case "CURRENT":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndEndAfterAndStartBefore(bookerId, LocalDateTime.now(), LocalDateTime.now(), sort));

            case "PAST":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndEndBefore(bookerId, LocalDateTime.now(), sort));
            case "FUTURE":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStartAfter(bookerId, LocalDateTime.now(), sort));
            case "WAITING":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.WAITING, sort));
            case "REJECTED":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByBooker_IdAndStatus(bookerId, Status.REJECTED, sort));
            default:
                throw new UnavalibleException("Unknown state: " + status);
        }
    }


    @Override
    public List<BookingRequestDto> getBookingsOwner(Long bookerId, String status) {
        checkUser(bookerId);

        Sort sort = Sort.by("start").descending();

        switch (status) {
            case "ALL":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_Owner(bookerId, sort));

            case "CURRENT":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndEndAfterAndStartBefore(bookerId, LocalDateTime.now(), LocalDateTime.now(), sort));

            case "PAST":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndEndBefore(bookerId, LocalDateTime.now(), sort));
            case "FUTURE":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStartAfter(bookerId, LocalDateTime.now(), sort));
            case "WAITING":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.WAITING, sort));
            case "REJECTED":
                return BookingMapper.toListBookingDto(bookingRepository.findAllByItem_OwnerAndStatus(bookerId, Status.REJECTED, sort));
            default:
                throw new UnavalibleException("Unknown state: " + status);
        }
    }


    private void checkUser(Long userId) {
        if (userId == null) {
            throw new DataNotFoundException("не указан id пользователя");
        }

        if (userRepository.findAllById(Collections.singleton(userId)).isEmpty()) {
            throw new DataNotFoundException("Не найден id пользователя");
        }
    }

    private void checkDate(BookingDto bookingDto) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new BookingTimeException("Некорректно указаны даты бронирования");
        }
    }

    @Override
    public BookingRequestDto findById(long userId, long bookingId) {

        if (bookingRepository.findAllById(Collections.singleton(bookingId)).isEmpty()) {
            throw new DataNotFoundException("Не найден id бронирования");
        }

        if (userId != bookingRepository.getReferenceById(bookingId).getItem().getOwner() &&
                userId != bookingRepository.getReferenceById(bookingId).getBooker().getId()) {
            throw new DataNotFoundException("У вас нет доступа к этим данным");
        }

        try {
            Booking newBooking = bookingRepository.getReferenceById(bookingId);
            return BookingMapper.toBookingReqwDto(newBooking);
        } catch (DataIntegrityViolationException e) {
            throw new DataNotFoundException("неверно указан пользователь");
        }
    }
}
