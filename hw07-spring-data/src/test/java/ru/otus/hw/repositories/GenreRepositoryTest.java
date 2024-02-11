package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@DisplayName("Тестирование репозитория жанров")
@DataJpaTest(showSql = false)
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Поиск всех жанров")
    void checkFindAllAuthors() {
        List<Genre> repositoryGenres = repository.findAll();
        List<Genre> expectedGenres = LongStream.range(1, 4).mapToObj(i -> testEntityManager.find(Genre.class, i)).toList();

        Assertions.assertAll("Проверка корректности поиска жанров", () -> {
            Assertions.assertEquals(repositoryGenres.size(), expectedGenres.size(), "Проверка количества жанров");
            Assertions.assertTrue(repositoryGenres.containsAll(expectedGenres));
        });
    }

    @Test
    @DisplayName("Поиск первого жанра")
    void findFirstGenre() {
        Optional<Genre> repositoryGenre = repository.findById(1L);
        Genre expectedGenre = testEntityManager.find(Genre.class, 1L);

        repositoryGenre.ifPresentOrElse(genre -> Assertions.assertAll("Проверка корректности поиска жанра", () -> {
            Assertions.assertEquals(genre.getId(), expectedGenre.getId(), "Проверка ID жанра");
            Assertions.assertEquals(genre.getName(), expectedGenre.getName(), "Проверка названия жанра");
        }), () -> {
            throw new EntityNotFoundException("Такой жанр отстутствует");
        });
    }
}