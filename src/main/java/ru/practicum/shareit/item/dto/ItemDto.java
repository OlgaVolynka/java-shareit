package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.BookingRequestForItemDto;
import ru.practicum.shareit.item.model.Comments;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
public class ItemDto {

    private Long id;
    @NotBlank(groups = Marker.OnCreate.class)
    private String name;
    @NotBlank(groups = Marker.OnCreate.class)
    private String description;
    @NotNull(groups = Marker.OnCreate.class)
    private Boolean available;
    private BookingRequestForItemDto lastBooking;
    private BookingRequestForItemDto nextBooking;
    private List<Comments> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(id, itemDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}