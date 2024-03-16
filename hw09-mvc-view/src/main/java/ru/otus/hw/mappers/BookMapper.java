package ru.otus.hw.mappers;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

public class BookMapper {

    public static BookDto toBookDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
    }
}
