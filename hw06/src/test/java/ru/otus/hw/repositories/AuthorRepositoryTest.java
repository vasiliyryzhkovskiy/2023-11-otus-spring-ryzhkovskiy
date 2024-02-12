package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория авторов")
@DataJpaTest(showSql = false)
@Import(JpaAuthorRepository.class)
public class AuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск всех авторов")
    void findAllAuthors() {
        List<Author> repositoryAuthors = repository.findAll();
        List<Author> expectedAuthors = LongStream.range(1, 4).mapToObj(i -> testEntityManager.find(Author.class, i)).toList();

        Assertions.assertAll("Проверка корректности поиска авторов", () -> {
            Assertions.assertEquals(repositoryAuthors.size(), expectedAuthors.size(), "Проверка количества авторов");

            repositoryAuthors.forEach(author -> {
                Assertions.assertEquals(expectedAuthors
                                .stream()
                                .filter(author1 -> author.getId() == author1.getId())
                                .toList()
                                .getFirst()
                                .getFullName(),
                        author.getFullName());
            });
        });
    }
}