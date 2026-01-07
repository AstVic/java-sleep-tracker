package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String russianName;

    Chronotype(String russianName) {
        this.russianName = russianName;
    }
}