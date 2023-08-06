package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestDtoTest {

    @Test
    void getDescription() {

        RequestDto request = new RequestDto();

        LocalDateTime data = LocalDateTime.now();

        request.setCreated(data);
        request.setDescription("Description");

        LocalDateTime data1 = request.getCreated();
        String description = request.getDescription();

        assertEquals(data1, data);
        assertEquals("Description", description);

        assertEquals(data, data1);
        assertEquals("Description", description);


    }

  }