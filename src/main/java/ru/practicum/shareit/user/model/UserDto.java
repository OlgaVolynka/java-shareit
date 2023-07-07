package ru.practicum.shareit.user.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class UserDto {

    private long id;
    @NotBlank(groups = Marker.OnCreate.class)
    private String name;
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @", groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnCreate.class)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
