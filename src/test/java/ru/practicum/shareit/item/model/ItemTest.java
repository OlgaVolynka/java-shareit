package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testEquals() {

        Item item = new Item();
        List<Comments> commentsList = List.of(new Comments());
        Item item1 = new Item();
        item1.setId(1L);

        item.setAvailable(true);
        item.setDescription("Description");
        item.setOwner(1L);
        item.setName("name");
        item.setId(1L);
        item.setComments(commentsList);
        item.setRequestId(1L);

        Boolean available = item.getAvailable();
        String description = item.getDescription();
        Long id = item.getId();
        String name = item.getName();
        Long idOwner = item.getOwner();
        List<Comments> commentsList1 = item.getComments();
        Long idRequest = item.getRequestId();

        int hash = item.hashCode();
        int hash1 = Objects.hash(id);

        assertEquals(hash, hash1);

        assertEquals(true, available);
        assertEquals("Description", description);
        assertEquals(1L, id);
        assertEquals(1L, idOwner);
        assertEquals(1L, idRequest);
        assertEquals("name", name);
        assertEquals(commentsList, commentsList1);
        assertEquals(item1, item);

    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getAvailable() {
    }

    @Test
    void getOwner() {
    }

    @Test
    void getComments() {
    }

    @Test
    void getRequestId() {
    }

    @Test
    void setId() {
    }

    @Test
    void setName() {
    }

    @Test
    void setDescription() {
    }

    @Test
    void setAvailable() {
    }

    @Test
    void setOwner() {
    }

    @Test
    void setComments() {
    }

    @Test
    void setRequestId() {
    }
}