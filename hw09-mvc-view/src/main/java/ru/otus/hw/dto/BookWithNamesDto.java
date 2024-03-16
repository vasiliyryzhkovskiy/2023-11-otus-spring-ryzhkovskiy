package ru.otus.hw.dto;

import lombok.Data;

@Data
public class BookWithNamesDto {

    private final Long id;

    private final String title;

    private final Long authorId;

    private final String authorName;

    private final Long genreId;

    private final String genreName;
}