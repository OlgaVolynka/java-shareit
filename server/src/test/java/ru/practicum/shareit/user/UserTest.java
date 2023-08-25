package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

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



        assertEquals("email@email.ru", email);
        assertEquals("name", name);
        assertEquals(1L, id);
        assertEquals(user, user2);


    }

}