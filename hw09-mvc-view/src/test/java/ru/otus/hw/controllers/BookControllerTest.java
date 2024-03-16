package ru.otus.hw.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookWithNamesDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тесты контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {
    private static final BookDto EXPECTED_BOOK = new BookDto(1L, "BookTitle_1", 1L, 1L);

    private static final BookWithNamesDto EXPECTED_BOOK_WITH_NAMES = new BookWithNamesDto(
            1L, "Evgeniy Onegin", 1L, "Author_1", 1L, "Genre_1");

    private static final AuthorDto EXPECTED_AUTHOR = new AuthorDto(1L, "Author_1");

    private static final GenreDto EXPECTED_GENRE = new GenreDto(1L, "Genre_1");



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookServiceImpl bookService;

    private List<BookDto> books;

    @BeforeEach
    void setUp() {
        books = List.of(
                new BookDto(1L, "BookTitle_1", 1L, 1L),
                new BookDto(2L, "BookTitle_2", 2L, 2L),
                new BookDto(3L, "BookTitle_3", 3L, 3L)
        );
    }

    @DisplayName("должен возвращать все книги")
    @Test
    void findAllBooks() throws Exception {
        List<BookWithNamesDto> books = Collections.singletonList(
                EXPECTED_BOOK_WITH_NAMES);
        when(bookService.getAllWithGenreAndAuthorNames()).thenReturn(books);

        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("book/list"));

        verify(bookService, times(1)).getAllWithGenreAndAuthorNames();
    }

    @DisplayName("должен возвращать книгу по id")
    @Test
    void findBookById() throws Exception {
        BookDto bookDto = books.get(0);
        List<AuthorDto> authors = Collections.singletonList(EXPECTED_AUTHOR);
        List<GenreDto> genres = Collections.singletonList(EXPECTED_GENRE);
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);
        when(bookService.findById(anyLong())).thenReturn((bookDto));

        mockMvc.perform(get("/book/update/%s".formatted(bookDto.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("updateBook", EXPECTED_BOOK))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name("book/edit"));

        verify(bookService, times(1)).findById(EXPECTED_BOOK.getId());
        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
    }

    @DisplayName("должен добавлять новую книгу")
    @Test
    void newBook() throws Exception {

        mockMvc.perform(post("/book/new")
                        .params(new LinkedMultiValueMap<>() {{
                            add("title", EXPECTED_BOOK.getTitle());
                            add("authorId", String.valueOf(EXPECTED_BOOK.getAuthorId()));
                            add("genreId", String.valueOf(EXPECTED_BOOK.getGenreId()));
                        }})
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        verify(bookService, times(1))
                .insert(EXPECTED_BOOK.getTitle(), EXPECTED_BOOK.getAuthorId(), EXPECTED_BOOK.getGenreId());

    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void deleteByBookId() throws Exception {
        BookDto bookDto = books.get(0);

        mockMvc.perform(post("/book/delete/" + bookDto.getId()))
                .andExpect(redirectedUrl("/book"));

        verify(bookService, times(1)).deleteById(bookDto.getId());
    }
}