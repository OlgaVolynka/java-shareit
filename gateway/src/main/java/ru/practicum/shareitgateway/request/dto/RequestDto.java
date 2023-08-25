package ru.practicum.shareitgateway.request.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RequestDto {

    @NotNull
    private String description;
    private LocalDateTime created = LocalDateTime.now();
}
