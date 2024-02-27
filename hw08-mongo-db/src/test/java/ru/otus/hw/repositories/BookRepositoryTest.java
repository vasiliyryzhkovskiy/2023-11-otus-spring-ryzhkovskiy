package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование репозитория книг")
@DataMongoTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final List<Author> listAuthors = List.of(
            new Author("id_1", "Author_1"),
            new Author("id_2", "Author_2"),
            new Author("id_3", "Author_3"));

    private final List<Genre> listGenres = List.of(
            new Genre("id_1", "Genre_1"),
            new Genre("id_2", "Genre_2"),
            new Genre("id_3", "Genre_3"));

    private final List<Book> listBooks = List.of(
            new Book("id_1", "Book_1", listAuthors.get(0), listGenres.get(0)),
            new Book("id_2", "Book_2", listAuthors.get(1), listGenres.get(1)),
            new Book("id_3", "Book_3", listAuthors.get(0), listGenres.get(0)));

    @BeforeEach
    void beforeEach() {
        mongoTemplate.dropCollection(Book.class);
        listBooks.forEach(book -> mongoTemplate.save(book));
    }

    @Test
    @DisplayName("Поиск всех книг")
    void checkFindAllBooks() {
        List<Book> repositoryBooks = repository.findAll();
        List<Book> expectedBooks = listBooks;

        Assertions.assertAll("Проверка корректности поиска книг", () -> {
            Assertions.assertEquals(repositoryBooks.size(), expectedBooks.size(), "Проверка количества книг");
            Assertions.assertTrue(repositoryBooks.containsAll(expectedBooks));
        });
    }

    @Test
    @DisplayName("Добавление новой книги")
    void saveNewBook() {
        Author author = listAuthors.getFirst();
        Genre genre = listGenres.getFirst();
        Book book = new Book("id_4", "BookTitle_New", author, genre);
        Book savedBook = repository.save(new Book("id_4", "BookTitle_New", author, genre));

        Assertions.assertAll("Проверка корректности добавления книги", () -> {
            Assertions.assertNotNull(savedBook);
            Assertions.assertEquals(savedBook, book);
            Assertions.assertEquals(savedBook.getTitle(), book.getTitle());
            Assertions.assertEquals(savedBook.getId(), book.getId());
        });
    }

    @Test
    @DisplayName("Удаление новой книги")
    void deleteNewBook() {
        Author author = listAuthors.getFirst();
        Genre genre = listGenres.getFirst();
        Book book = new Book("id_5", "BookTitle_New", author, genre);
        Book savedBook = repository.save(new Book("id_5", "BookTitle_New", author, genre));

        Assertions.assertAll("Проверка корректности добавления книги", () -> {
            Assertions.assertNotNull(book);
            Assertions.assertEquals(savedBook, book);
            Assertions.assertEquals(savedBook.getTitle(), book.getTitle());
            Assertions.assertEquals(savedBook.getId(), book.getId());
        });

        repository.deleteById(savedBook.getId());
        Assertions.assertAll("Проверка корректности удаления книги", () -> {
            Book findBook = mongoTemplate.findById(savedBook.getId(), Book.class);
            Assertions.assertNull(findBook);
        });
    }

    @Test
    @DisplayName("Поиск первой книги")
    void findFirstBook() {
        Optional<Book> repositoryBook = repository.findById(listBooks.getFirst().getId());
        Book expectedBook = listBooks.getFirst();

        repositoryBook.ifPresentOrElse(book -> Assertions.assertAll("Проверка корректности поиска книги", () -> {
            Assertions.assertEquals(book.getId(), expectedBook.getId(), "Проверка ID книги");
            Assertions.assertEquals(book.getTitle(), expectedBook.getTitle(), "Проверка имени книги");
        }), () -> {
            throw new EntityNotFoundException("Такая книга отстутствует");
        });
    }
}