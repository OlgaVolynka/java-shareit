package ru.practicum.shareit.booking.model.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;


@Data
public class BookingRequestDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private User booker;
    private Status status;

}
