package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testEquals() {

        User user = new User();
        user.setEmail("email@email.ru");
        user.setId(1L);
        user.setName("name");

        User user2 = new User();
        user2.setId(1L);

        String email = user.getEmail();
        String name = user.getName();
        Long id = user.getId();

        int hash = user.hashCode();
        int hash1 = Objects.hash(id);

        assertEquals(hash, hash1);

        assertEquals("email@email.ru", email);
        assertEquals("name", name);
        assertEquals(1L, id);
        assertEquals(user, user2);
        assertEquals(hash, Objects.hash(id));

    }

}