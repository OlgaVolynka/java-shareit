package ru.practicum.shareit.booking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestForItemDto {
    private Long Id;
    private Long bookerId;
}