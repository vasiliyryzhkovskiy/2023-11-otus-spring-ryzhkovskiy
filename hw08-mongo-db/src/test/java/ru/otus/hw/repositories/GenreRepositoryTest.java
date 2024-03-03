package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование репозитория жанров")
@DataMongoTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository repository;

    private final List<Genre> listGenres = List.of(
            new Genre("id_1", "Genre_1"),
            new Genre("id_2", "Genre_2"),
            new Genre("id_3", "Genre_3"));

    @Test
    @DisplayName("Поиск всех жанров")
    void findAllAuthors() {
        List<Genre> repositoryGenres = repository.findAll();
        List<Genre> expectedGenres = listGenres;

        Assertions.assertAll("Проверка корректности поиска жанров", () -> {
            Assertions.assertEquals(repositoryGenres.size(), expectedGenres.size(), "Проверка количества жанров");
            Assertions.assertTrue(repositoryGenres.containsAll(expectedGenres));
        });
    }

    @Test
    @DisplayName("Поиск первого жанра")
    void findFirstGenre() {
        Optional<Genre> repositoryGenre = repository.findById(listGenres.getFirst().getId());
        Genre expectedGenre = listGenres.getFirst();

        repositoryGenre.ifPresentOrElse(genre -> Assertions.assertAll("Проверка корректности поиска жанра", () -> {
            Assertions.assertEquals(genre.getId(), expectedGenre.getId(), "Проверка ID жанра");
            Assertions.assertEquals(genre.getName(), expectedGenre.getName(), "Проверка названия жанра");
        }), () -> {
            throw new EntityNotFoundException("Такой жанр отстутствует");
        });
    }
}