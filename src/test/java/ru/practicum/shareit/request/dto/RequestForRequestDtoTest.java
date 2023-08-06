package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

class RequestForRequestDtoTest {

    @Test
    void getId() {
        LocalDateTime data = LocalDateTime.now();

        RequestForRequestDto request = new RequestForRequestDto();
        request.setRequestMarker(new User());
        request.setId(1L);
        request.setCreated(data);
        request.setDescription("Description");

    }
}