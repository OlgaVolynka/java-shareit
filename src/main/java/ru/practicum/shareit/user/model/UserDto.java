package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    @NotBlank(groups = Marker.OnCreate.class)
    private String name;
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @",
            groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @NotNull(groups = Marker.OnCreate.class)
    private String email;

}
