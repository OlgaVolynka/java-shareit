package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @Test
    void setId() {

        Request request = new Request();
        Request request1 = new Request();
        request1.setId(1L);
        LocalDateTime data = LocalDateTime.now();

        User user = new User();
        Set<Item> set = Set.of(new Item());

        request.setRequestMarker(user);
        request.setCreated(data);
        request.setDescription("Description");
        request.setId(1L);
        request.setItems(set);

        Set<Item> set1 = request.getItems();
        User user1 = request.getRequestMarker();
        LocalDateTime data1 = request.getCreated();
        String description = request.getDescription();
        Long id = request.getId();

        int hash = request.hashCode();
        int hash1 = Objects.hash(id);

        assertEquals(hash, hash1);
        assertEquals(set, set1);
        assertEquals(user, user1);
        assertEquals(data1, data);
        assertEquals("Description", description);
        assertEquals(1L, id);
        assertEquals(request, request1);


    }
}