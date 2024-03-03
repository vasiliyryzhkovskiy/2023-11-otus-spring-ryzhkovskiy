package ru.otus.hw.services.interfaces;

import ru.otus.hw.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(String id);

    List<BookDto> findAll();

    BookDto insert(String title, String authorId, String genresId);

    BookDto update(String id, String title, String authorId, String genresId);

    void deleteById(String id);
}
