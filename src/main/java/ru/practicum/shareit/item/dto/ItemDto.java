package ru.practicum.shareit.item.dto;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private Boolean available;
    public ItemDto(String name, String description, Boolean available) {

        this.name = name;
        this.description = description;
        this.available = available;
    }
}