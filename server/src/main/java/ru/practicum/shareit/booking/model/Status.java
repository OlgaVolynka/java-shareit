package ru.practicum.shareit.booking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bookings", schema = "public")

public enum Status {
    WAITING,
    APPROVED,
    REJECTED;
}
