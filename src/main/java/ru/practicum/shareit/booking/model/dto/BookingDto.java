package ru.practicum.shareit.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.booking.model.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long id;
    @NotNull(groups = Marker.OnCreate.class)
    @Future(groups = Marker.OnCreate.class)
    private LocalDateTime start;
    @NotNull(groups = Marker.OnCreate.class)
    @Future(groups = Marker.OnCreate.class)
    private LocalDateTime end;
    @NotNull(groups = Marker.OnCreate.class)
    private Long itemId;
    private Status status;
}
