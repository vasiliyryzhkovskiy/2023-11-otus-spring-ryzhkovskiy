package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookWithNamesDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.mappers.BookWithNamesMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookWithNamesDto> getAllWithGenreAndAuthorNames() {
        return bookRepository
                .findAllFetchAuthorsAndGenres()
                .stream()
                .map(BookWithNamesMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        return BookMapper.toBookDto(
                bookRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)))
        );
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookMapper::toBookDto).toList();
    }

    @Override
    @Transactional
    public void insert(String title, long authorId, long genreId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        Book book = new Book(0, title, author, genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(long id, String title, long authorId, long genreId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}