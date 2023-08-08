package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.User;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class RequestForRequestDtoTest {

    @Autowired
    private JacksonTester<RequestForRequestDto> json;

    @Test
    void requestForRequestDtoJsonTest() throws IOException {
        LocalDateTime data = LocalDateTime.now();
        User user = new User();
        user.setId(1L);

        RequestForRequestDto request = new RequestForRequestDto();
        request.setRequestMarker(user);
        request.setId(1L);
        request.setCreated(data);
        request.setDescription("Description");

        JsonContent<RequestForRequestDto> result = json.write(request);

        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Description");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(data.toString());
        assertThat(result).extractingJsonPathNumberValue("$.requestMarker.id").isEqualTo(1);

    }
}