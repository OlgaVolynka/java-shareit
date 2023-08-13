package ru.practicum.shareitgateway.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareitgateway.Marker;
import ru.practicum.shareitgateway.booking.BookingState;


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
    private BookingState status;
}
