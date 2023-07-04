package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;

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

    public Item(String name, String description, boolean available, Long owner) {

        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
    public Item() {
    }
}