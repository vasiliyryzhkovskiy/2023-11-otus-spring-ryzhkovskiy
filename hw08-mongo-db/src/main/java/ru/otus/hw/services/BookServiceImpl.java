package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.interfaces.BookService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(String id) {
        return bookRepository.findById(id)
                .map(BookDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookDto::new)
                .toList();
    }

    @Override
    @Transactional
    public BookDto insert(String title, String authorId, String genreId) {
        return new BookDto(save(null, title, authorId, genreId));
    }

    @Override
    @Transactional
    public BookDto update(String id, String title, String authorId, String genreId) {
        return new BookDto(save(id, title, authorId, genreId));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        findById(id);
        bookRepository.deleteById(id);
        commentRepository.deleteByBookId(id);
    }

    private Book save(String id, String title, String authorId, String genreId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(genreId)));
        ;
        Book book = new Book(id, title, author, genre);
        return bookRepository.save(book);
    }

}
