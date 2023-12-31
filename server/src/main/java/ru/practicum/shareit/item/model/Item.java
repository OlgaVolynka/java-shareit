package ru.practicum.shareit.item.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "description")
    @NotNull
    private String description;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "owner")
    private Long owner;
    @OneToMany
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private List<Comments> comments;
    @Column(name = "requests_id")
    private Long requestId;

}