package ru.practicum.shareit.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */



@Data
public class User {

    private long id;

    private String name;
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    @NotNull
    private String email;

}