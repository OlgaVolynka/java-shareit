package ru.practicum.shareitgateway.item.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDto {

    private Long id;
    private String text;
    private Long itemId;
    private Long authorId;
    private LocalDateTime created;
    private String authorName;

}
