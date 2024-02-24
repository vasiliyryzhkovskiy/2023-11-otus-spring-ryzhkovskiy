package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;


    public BookDto(Book book) {
        this(book.getId(), book.getTitle(), new AuthorDto(book.getAuthor()), new GenreDto(book.getGenre()));
    }
}
