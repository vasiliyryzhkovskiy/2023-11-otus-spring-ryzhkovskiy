package ru.otus.hw.mappers;


import ru.otus.hw.dto.BookWithNamesDto;
import ru.otus.hw.models.Book;

public class BookWithNamesMapper {

    public static BookWithNamesDto toDto(Book book) {
        return new BookWithNamesDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getAuthor().getFullName(),
                book.getGenre().getId(),
                book.getGenre().getName());

    }
}
