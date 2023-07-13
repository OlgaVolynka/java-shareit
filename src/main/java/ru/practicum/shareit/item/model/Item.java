package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
public class Item {

    private long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}