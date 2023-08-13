package ru.practicum.shareitgateway.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    @NotEmpty
    private String text;
    @Column(name = "item_Id")

    private Long itemId;
    @Column(name = "author_Id")

    private Long authorId;
    @Column(name = "created_time")
    private LocalDateTime created = LocalDateTime.now();
    @Column(name = "author_Name")
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
