package ru.practicum.shareit.item.model;

import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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