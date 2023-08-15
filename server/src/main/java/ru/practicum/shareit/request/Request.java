package ru.practicum.shareit.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "requests", schema = "public")
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Request {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "created_time")
    private LocalDateTime created;

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


}
