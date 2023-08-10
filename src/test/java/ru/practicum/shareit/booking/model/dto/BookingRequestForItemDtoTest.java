package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class BookingRequestForItemDtoTest {

    @Autowired
    private JacksonTester<BookingRequestForItemDto> json;

    @Test
    void bookingRequestForItemDtoJsonTest() throws IOException {

        BookingRequestForItemDto booking = new BookingRequestForItemDto();
        booking.setId(1L);
        booking.setBookerId(2L);

        JsonContent<BookingRequestForItemDto> result = json.write(booking);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.bookerId").isEqualTo(2);
    }
}