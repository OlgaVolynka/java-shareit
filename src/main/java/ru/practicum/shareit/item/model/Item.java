package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items", schema = "public")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
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


   /* @OneToMany
    @JoinColumn (name = "item_id")
    @ToString.Exclude
    private Set<Booking> bookings;

 */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}