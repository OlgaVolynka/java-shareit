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
class CommentRequestDtoTest {

    @Autowired
    private JacksonTester<CommentRequestDto> json;


    @Test
    void commentRequestDtoJsonTest() throws IOException {

        LocalDateTime data = LocalDateTime.now();

        CommentRequestDto comment = new CommentRequestDto();
        comment.setText("text");
        comment.setCreated(data);
        comment.setAuthorId(1L);
        comment.setItemId(1L);
        comment.setAuthorName("AuthorName");
        comment.setId(1L);

        JsonContent<CommentRequestDto> result = json.write(comment);

    //    assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(data.toString());
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("text");

        assertThat(result).extractingJsonPathNumberValue("$.authorId").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("AuthorName");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);

    }
}