package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookWithNamesDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService,
                          AuthorService authorService,
                          GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("book")
    public String getAll(Model model) {
        List<BookWithNamesDto> books = bookService.getAllWithGenreAndAuthorNames();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("book/new")
    public String create(Model model) {
        model.addAttribute("updateBook", new BookDto());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "book/add";
    }

    @PostMapping("book/new")
    public String add(@Valid @ModelAttribute("updateBook") BookDto book, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("authors", authorService.findAll());
            return "book/add";
        }
        bookService.insert(book.getTitle(), book.getAuthorId(), book.getGenreId());
        return "redirect:/book";
    }


    @GetMapping("book/update/{id}")
    public String edit(@PathVariable("id") long id,
                       Model model) {

        BookDto updateBook = bookService.findById(id);
        model.addAttribute("updateBook", updateBook);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book/edit";
    }

    @PostMapping("book/update/{id}")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("updateBook") BookDto book,
                         Errors errors,
                         Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/edit";
        }
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
        return "redirect:/book";
    }

    @PostMapping("book/delete/{id}")
    public String remove(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
