package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoTest {

    @Test
    void getText() {

        LocalDateTime data = LocalDateTime.now();

        CommentDto comment = new CommentDto();
        comment.setText("text");
        comment.setCreated(data);
        String text = comment.getText();
        LocalDateTime data1 = comment.getCreated();

        assertEquals("text", text);
        assertEquals(data, data1);
    }
}