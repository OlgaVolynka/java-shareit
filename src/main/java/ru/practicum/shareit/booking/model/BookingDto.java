package ru.practicum.shareit.booking.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
