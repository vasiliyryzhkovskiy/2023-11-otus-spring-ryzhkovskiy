package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория книг")
@DataJpaTest(showSql = false)
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск всех книг")
    void checkFindAllBooks() {
        List<Book> repositoryBooks = repository.findAll();
        List<Book> expectedBooks = LongStream.range(1, 4).mapToObj(i -> testEntityManager.find(Book.class, i)).toList();

        Assertions.assertAll("Проверка корректности поиска книг", () -> {
            Assertions.assertEquals(repositoryBooks.size(), expectedBooks.size(), "Проверка количества книг");
            Assertions.assertTrue(repositoryBooks.containsAll(expectedBooks));
        });
    }

    @Test
    @DisplayName("Добавление новой книги")
    void saveNewBook() {
        Author author = testEntityManager.find(Author.class, 1);
        Genre genre = testEntityManager.find(Genre.class, 1);
        Book book = repository.save(new Book(0, "BookTitle_New", author, genre));

        Assertions.assertAll("Проверка корректности добавления книги", () -> {
            Book findBook = testEntityManager.find(Book.class, book.getId());
            Assertions.assertEquals(findBook, book);
            Assertions.assertEquals(findBook.getTitle(), book.getTitle());
        });
    }

    @Test
    @DisplayName("Удаление новой книги")
    void deleteNewBook() {
        Author author = testEntityManager.find(Author.class, 1);
        Genre genre = testEntityManager.find(Genre.class, 1);
        Book book = repository.save(new Book(0, "BookTitle_New", author, genre));

        Assertions.assertAll("Проверка корректности добавления книги", () -> {
            Book findBook = testEntityManager.find(Book.class, book.getId());
            Assertions.assertEquals(findBook, book);
            Assertions.assertEquals(findBook.getTitle(), book.getTitle());
        });

        repository.deleteById(book.getId());
        Assertions.assertAll("Проверка корректности удаления книги", () -> {
            Book findBook = testEntityManager.find(Book.class, book.getId());
            Assertions.assertNull(findBook);
        });
    }

    @Test
    @DisplayName("Поиск первой книги")
    void findFirstBook() {
        Optional<Book> repositoryBook = repository.findById(1L);
        Book expectedBook = testEntityManager.find(Book.class, 1L);

        repositoryBook.ifPresentOrElse(book -> Assertions.assertAll("Проверка корректности поиска книги", () -> {
            Assertions.assertEquals(book.getId(), expectedBook.getId(), "Проверка ID книги");
            Assertions.assertEquals(book.getTitle(), expectedBook.getTitle(), "Проверка имени книги");
        }), () -> {
            throw new EntityNotFoundException("Такая книга отстутствует");
        });
    }
}