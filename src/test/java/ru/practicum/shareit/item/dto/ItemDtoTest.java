package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comments;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDtoTest {

    @Test
    void getId() {

        ItemDto item = new ItemDto();
        List<Comments> commentsList = List.of(new Comments());
        ItemDto item1 = new ItemDto();
        item1.setId(1L);

        item.setAvailable(true);
        item.setDescription("Description");

        item.setName("name");
        item.setId(1L);
        item.setComments(commentsList);
        item.setRequestId(1L);

        Boolean available = item.getAvailable();
        String description = item.getDescription();
        Long id = item.getId();
        String name = item.getName();
        List<Comments> commentsList1 = item.getComments();
        Long idRequest = item.getRequestId();

        assertEquals(true, available);
        assertEquals("Description", description);
        assertEquals(1L, id);

        assertEquals(1L, idRequest);
        assertEquals("name", name);
        assertEquals(commentsList, commentsList1);

    }
}