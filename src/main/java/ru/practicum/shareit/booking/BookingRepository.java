package ru.practicum.shareit.booking;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {

    List<Booking> findByBookerId(Long bookerId, Sort sort);

    List<Booking> findAllByItem_Owner(Long bookerId, Sort sort);

    List<Booking> findAllByItem_OwnerAndStatus(Long bookerId, Status status, Sort sort);

    List<Booking> findAllByItem_IdAndStartAfterAndStatusOrderByStartAsc(Long itemId, LocalDateTime nowDataTime, Status status);

    List<Booking> findAllByItem_IdAndStartBeforeAndStatusOrderByStartDesc(Long itemId, LocalDateTime nowDataTime, Status status);

    List<Booking> findAllByBooker_IdAndStatus(Long bookerId, Status status, Sort sort);

    List<Booking> findAllByBooker_IdAndEndBefore(Long bookerId, LocalDateTime nowDataTime, Sort sort);
    List<Booking> findAllByBooker_IdAndStartAfter(Long bookerId, LocalDateTime nowDataTime, Sort sort);


    List<Booking> findAllByBooker_IdAndEndAfterAndStartBefore(Long bookerId, LocalDateTime nowDataTime1, LocalDateTime nowDataTime, Sort sort);

    List<Booking> findAllByItem_OwnerAndEndAfterAndStartBefore(Long itemId, LocalDateTime nowDataTime1, LocalDateTime nowDataTime, Sort sort);

    List<Booking> findAllByItem_OwnerAndEndBefore(Long itemId, LocalDateTime nowDataTime1,  Sort sort);

    List<Booking> findAllByBooker_IdAndItem_IdAndEndBefore(Long bookerId, Long itemId, LocalDateTime dateTime );
    List<Booking> findAllByItem_OwnerAndStartAfter(Long itemId, LocalDateTime nowDataTime1,  Sort sort);

}
