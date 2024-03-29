package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория жанров")
@DataJpaTest(showSql = false)
@Import(JpaGenreRepository.class)
public class GenreRepositoryTest {
    @Autowired
    private JpaGenreRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск всех жанров")
    void checkFindAllAuthors() {
        List<Genre> repositoryGenres = repository.findAll();
        List<Genre> expectedGenres = LongStream.range(1, 4).mapToObj(i -> testEntityManager.find(Genre.class, i)).toList();

        Assertions.assertAll("Проверка корректности поиска жанров", () -> {
            Assertions.assertEquals(repositoryGenres.size(), expectedGenres.size(), "Проверка количества жанров");

            repositoryGenres.forEach(genre -> {
                Assertions.assertEquals(expectedGenres
                                .stream()
                                .filter(genre1 -> genre.getId() == genre1.getId())
                                .toList()
                                .getFirst()
                                .getName(),
                        genre.getName());
            });
        });
    }
}