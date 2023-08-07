package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class RequestDtoTest {

    @Autowired
    private JacksonTester<RequestDto> json;

    @Test
    void testRequestDto() throws Exception {
        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("description");


        JsonContent<RequestDto> result = json.write(requestDto);

        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
    }
}