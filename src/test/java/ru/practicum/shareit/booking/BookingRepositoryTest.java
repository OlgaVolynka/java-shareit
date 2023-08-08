package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void findByBookerId() {
    }

    @Test
    void testFindByBookerId() {
    }

    @Test
    void findAllByItem_Owner() {
    }

    @Test
    void findAllByItem_OwnerAndStatus() {
    }

    @Test
    void findAllByItem_IdAndStartAfterAndStatusOrderByStartAsc() {
    }

    @Test
    void findAllByItem_IdAndStartBeforeAndStatusOrderByStartDesc() {
    }

    @Test
    void findAllByBooker_IdAndStatus() {
    }

    @Test
    void findAllByBooker_IdAndEndBefore() {
    }

    @Test
    void findAllByBooker_IdAndStartAfter() {
    }

    @Test
    void findAllByBooker_IdAndEndAfterAndStartBefore() {
    }

    @Test
    void findAllByItem_OwnerAndEndAfterAndStartBefore() {
    }

    @Test
    void findAllByItem_OwnerAndEndBefore() {
    }

    @Test
    void findAllByBooker_IdAndItem_IdAndEndBefore() {
    }

    @Test
    void findAllByItem_OwnerAndStartAfter() {
    }

    @Test
    void testFindAllByItem_Owner() {
    }
}