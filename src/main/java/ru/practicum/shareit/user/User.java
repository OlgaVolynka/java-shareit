package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
public class User {

    private long id;

    private String name;

    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}