package ru.otus.hw.dto;

import lombok.Data;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Data
public class BookDto {
    private String id;

    private String title;

    private Author author;

    private Genre genre;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
    }
}
