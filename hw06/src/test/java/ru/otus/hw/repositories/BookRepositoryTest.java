package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория книг")
@DataJpaTest(showSql = false)
@Import(JpaBookRepository.class)
public class BookRepositoryTest {
    @Autowired
    private JpaBookRepository repository;

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
            Assertions.assertEquals(testEntityManager.find(Book.class, book.getId()), book);
            Assertions.assertEquals(testEntityManager.find(Book.class, book.getId()).getTitle(), book.getTitle());
        });
    }
}