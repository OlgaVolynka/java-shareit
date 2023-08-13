package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "requests", schema = "public")
@RequiredArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "created_time")
    private LocalDateTime created;
    //@Column (name = "requests_marker_id")
    @ManyToOne
    @JoinColumn(name = "requests_marker_id")
    private User requestMarker;
    @OneToMany
    @JoinColumn(name = "requests_id")
    private Set<Item> items;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
