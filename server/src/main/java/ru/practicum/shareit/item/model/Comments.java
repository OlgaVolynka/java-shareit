package ru.practicum.shareit.item.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
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

}
