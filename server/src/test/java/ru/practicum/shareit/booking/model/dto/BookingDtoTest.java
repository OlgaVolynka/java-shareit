package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.model.Status;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;

@JsonTest
class BookingDtoTest {
    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testBookingDto() throws Exception {
        LocalDateTime data = LocalDateTime.now();
        BookingDto bookingDto = new BookingDto(
                1L,
                data,
                data,
                1L,
                Status.APPROVED
        );

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(String.valueOf(Status.APPROVED));
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(data.toString());
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(data.toString());

    }
}