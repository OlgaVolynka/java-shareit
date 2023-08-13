package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String text;

    private LocalDateTime created = LocalDateTime.now();

}
