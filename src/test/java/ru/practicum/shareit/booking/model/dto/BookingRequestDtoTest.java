package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class BookingRequestDtoTest {

    @Autowired
    private JacksonTester<BookingRequestDto> json;

    @Test
    void testBookingRequestDto() throws Exception {

        LocalDateTime data = LocalDateTime.now();
        BookingRequestDto bookingDto = new BookingRequestDto();
        User user = new User();
        user.setId(2L);

        ItemDto item = new ItemDto();
        item.setId(1L);

        bookingDto.setId(1L);
        bookingDto.setStatus(Status.APPROVED);
        bookingDto.setEnd(data);
        bookingDto.setStart(data);
        bookingDto.setBooker(user);
        bookingDto.setItem(item);

        JsonContent<BookingRequestDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(String.valueOf(Status.APPROVED));
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(data.toString());
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(data.toString());
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(2);

    }
}