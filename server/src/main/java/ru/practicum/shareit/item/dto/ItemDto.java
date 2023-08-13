package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.dto.BookingRequestForItemDto;
import ru.practicum.shareit.item.model.Comments;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data

public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingRequestForItemDto lastBooking;
    private BookingRequestForItemDto nextBooking;
    private List<Comments> comments;
    private Long requestId;

}