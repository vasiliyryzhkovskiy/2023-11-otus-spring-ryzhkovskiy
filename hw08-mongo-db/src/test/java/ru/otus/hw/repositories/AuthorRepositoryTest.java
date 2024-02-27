package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование репозитория авторов")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    private final List<Author> listAuthors = List.of(
            new Author("id_1", "Author_1"),
            new Author("id_2", "Author_2"),
            new Author("id_3", "Author_3"));

    @Test
    @DisplayName("Поиск всех авторов")
    void findAllAuthors() {
        List<Author> repositoryAuthors = repository.findAll();
        List<Author> expectedAuthors = listAuthors;

        Assertions.assertAll("Проверка корректности поиска авторов", () -> {
            Assertions.assertEquals(repositoryAuthors.size(), expectedAuthors.size(), "Проверка количества авторов");
            Assertions.assertTrue(repositoryAuthors.containsAll(expectedAuthors));
        });
    }

    @Test
    @DisplayName("Поиск первого автора")
    void findFirstAuthor() {
        Optional<Author> repositoryAuthor = repository.findById(listAuthors.getFirst().getId());
        Author expectedAuthor = listAuthors.getFirst();

        repositoryAuthor.ifPresentOrElse(author -> Assertions.assertAll("Проверка корректности поиска автора", () -> {
            Assertions.assertEquals(author.getId(), expectedAuthor.getId(), "Проверка ID автора");
            Assertions.assertEquals(author.getFullName(), expectedAuthor.getFullName(), "Проверка имени автора");
        }), () -> {
            throw new EntityNotFoundException("Такой автор отстутствует");
        });
    }
}