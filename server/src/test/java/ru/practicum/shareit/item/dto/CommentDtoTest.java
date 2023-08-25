package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;


    @Test
    void getText() throws IOException {

        LocalDateTime data = LocalDateTime.now();

        CommentDto comment = new CommentDto();
        comment.setText("text");
        comment.setCreated(data);

        JsonContent<CommentDto> result = json.write(comment);

         assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("text");
    }
}