package ru.practicum.shareit.booking;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(Long bookerId, Pageable pageable);

    List<Booking> findAllByItem_Owner(Long bookerId, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndStatus(Long bookerId, Status status, Pageable pageable);

    List<Booking> findAllByItem_IdAndStartAfterAndStatusOrderByStartAsc(Long itemId, LocalDateTime nowDataTime, Status status);

    List<Booking> findAllByItem_IdAndStartBeforeAndStatusOrderByStartDesc(Long itemId, LocalDateTime nowDataTime, Status status);

    List<Booking> findAllByBooker_IdAndStatus(Long bookerId, Status status, Pageable pageable);

    List<Booking> findAllByBooker_IdAndEndBefore(Long bookerId, LocalDateTime nowDataTime, Pageable pageable);

    List<Booking> findAllByBooker_IdAndStartAfter(Long bookerId, LocalDateTime nowDataTime, Pageable pageable);

    List<Booking> findAllByBooker_IdAndEndAfterAndStartBefore(Long bookerId, LocalDateTime nowDataTime1, LocalDateTime nowDataTime, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndEndAfterAndStartBefore(Long itemId, LocalDateTime nowDataTime1, LocalDateTime nowDataTime, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndEndBefore(Long itemId, LocalDateTime nowDataTime1, Pageable pageable);

    List<Booking> findAllByBooker_IdAndItem_IdAndEndBefore(Long bookerId, Long itemId, LocalDateTime dateTime);

    List<Booking> findAllByItem_OwnerAndStartAfter(Long itemId, LocalDateTime nowDataTime1, Pageable pageable);


}
