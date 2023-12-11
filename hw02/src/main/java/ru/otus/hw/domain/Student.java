package ru.otus.hw.domain;

public record Student(String firstName, String lastName) {
    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }
}