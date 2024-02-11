package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория авторов")
@DataJpaTest(showSql = false)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск всех авторов")
    void findAllAuthors() {
        List<Author> repositoryAuthors = repository.findAll();
        List<Author> expectedAuthors = LongStream.range(1, 4).mapToObj(i -> testEntityManager.find(Author.class, i)).toList();

        Assertions.assertAll("Проверка корректности поиска авторов", () -> {
            Assertions.assertEquals(repositoryAuthors.size(), expectedAuthors.size(), "Проверка количества авторов");
            Assertions.assertTrue(repositoryAuthors.containsAll(expectedAuthors));
        });
    }

    @Test
    @DisplayName("Поиск первого автора")
    void findFirstAuthor() {
        Optional<Author> repositoryAuthor = repository.findById(1L);
        Author expectedAuthor = testEntityManager.find(Author.class, 1L);

        repositoryAuthor.ifPresentOrElse(author -> Assertions.assertAll("Проверка корректности поиска автора", () -> {
            Assertions.assertEquals(author.getId(), expectedAuthor.getId(), "Проверка ID автора");
            Assertions.assertEquals(author.getFullName(), expectedAuthor.getFullName(), "Проверка имени автора");
        }), () -> {
            throw new EntityNotFoundException("Такой автор отстутствует");
        });
    }
}