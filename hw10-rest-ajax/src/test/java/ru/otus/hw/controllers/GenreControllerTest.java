package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты по контроллеру жанров")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;


    @DisplayName("должен отображать список всех жанров")
    @Test
    void findAllGenres() throws Exception {
        List<GenreDto> genres = List.of(new GenreDto(1L, "Genre_1"),
                new GenreDto(2L, "Genre_2"),
                new GenreDto(3L, "Genre_3"));

        when(genreService.findAll()).thenReturn(genres);
        this.mvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(genres)));

    }

    @DisplayName("должен отображать жанр")
    @Test
    void getGenreById() throws Exception {
        GenreDto expectedGenreDto = new GenreDto(1L, "Genre_1");

        when(genreService.findById(expectedGenreDto.getId())).thenReturn(expectedGenreDto);
        this.mvc.perform(get("/api/v1/genres/{id}", expectedGenreDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedGenreDto)));
    }

    @DisplayName("должен редактировать жанр")
    @Test
    void getEditGenre() throws Exception {
        GenreDto updateGenreDto = new GenreDto(1L, "Genre_1");
        GenreDto expectedGenreDto = new GenreDto(1L, "Genre_1");

        when(genreService.update(any(Long.class), any(String.class))).thenReturn(expectedGenreDto);
        this.mvc.perform(put("/api/v1/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateGenreDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedGenreDto)));

        verify(genreService, times(1)).update(any(Long.class), any(String.class));
    }

    @DisplayName("должен создавать жанр")
    @Test
    void createNewGenre() throws Exception {
        GenreDto newGenre = new GenreDto(1L, "Genre_1");
        GenreDto expectedGenre = new GenreDto(1L, "Genre_1");

        when(genreService.update(any(Long.class), any(String.class))).thenReturn(expectedGenre);
        this.mvc.perform(post("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGenre)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedGenre)));

        verify(genreService, times(1)).update(any(Long.class), any(String.class));
    }
}