package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookWithNamesDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    List<BookWithNamesDto> getAllWithGenreAndAuthorNames();

    void insert(String title, long authorId, long genreId);

    void update(long id, String title, long authorId, long genreId);

    void deleteById(long id);
}
