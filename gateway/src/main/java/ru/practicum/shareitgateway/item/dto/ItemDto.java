package ru.practicum.shareitgateway.item.dto;

import lombok.Data;
import ru.practicum.shareitgateway.Marker;
import ru.practicum.shareitgateway.booking.dto.BookingRequestForItemDto;
import ru.practicum.shareitgateway.item.Comments;


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