package ru.practicum.shareitgateway.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    @NotNull
    private String text;

    private LocalDateTime created = LocalDateTime.now();

}
