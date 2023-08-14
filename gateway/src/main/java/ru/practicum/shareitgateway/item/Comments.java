package ru.practicum.shareitgateway.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
public class Comments {

    private Long id;

    @NotEmpty
    private String text;


    private Long itemId;


    private Long authorId;

    private LocalDateTime created = LocalDateTime.now();

    private String authorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return Objects.equals(id, comments.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
