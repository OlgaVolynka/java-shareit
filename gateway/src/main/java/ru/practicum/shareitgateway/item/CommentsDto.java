package ru.practicum.shareitgateway.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CommentsDto {

    private Long id;

    @NotEmpty
    private String text;


    private Long itemId;


    private Long authorId;

    private LocalDateTime created = LocalDateTime.now();

    private String authorName;

}
