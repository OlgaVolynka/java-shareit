package ru.practicum.shareit.exeption;

public class DataAlreadyExist extends RuntimeException {
    public DataAlreadyExist(String message) {

        super(message);
    }
}