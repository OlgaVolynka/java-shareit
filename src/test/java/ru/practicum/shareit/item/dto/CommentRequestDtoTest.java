package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentRequestDtoTest {

    @Test
    void getId() {

        LocalDateTime data = LocalDateTime.now();

        CommentRequestDto comment = new CommentRequestDto();
        comment.setText("text");
        comment.setCreated(data);
        comment.setAuthorId(1L);
        comment.setItemId(1L);
        comment.setAuthorName("AuthorName");
        comment.setId(1L);

        String text = comment.getText();
        LocalDateTime data1 = comment.getCreated();
        Long authtorId = comment.getAuthorId();
        long itemId = comment.getItemId();
        String name = comment.getAuthorName();
        Long id = comment.getId();

        assertEquals("text", text);
        assertEquals(data, data1);
        assertEquals(1L, authtorId);
        assertEquals(1L, itemId);
        assertEquals("AuthorName", name);
        assertEquals(1L, id);

    }
}