package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RequestForRequestDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    private User requestMarker;
    private Set<Item> items;

}
