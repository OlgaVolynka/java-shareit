package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.dto.BookingRequestForItemDto;
import ru.practicum.shareit.item.model.Comments;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data

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
    private Long requestId;

}