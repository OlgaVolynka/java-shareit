package ru.practicum.shareit.exeption;

public class WithoutXSharerUserId extends RuntimeException {

    public WithoutXSharerUserId(String message) {
        super(message);
    }
}

