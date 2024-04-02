package ru.otus.hw.controllers.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class GenrePageController {

    @GetMapping({"/genres"})
    public String getAllGenres() {
        return "genre-list";
    }

    @GetMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable("id") long id) {
        return "genre-edit";
    }

    @GetMapping("/genres/add")
    public String addAuthor() {
        return "genre-add";
    }

}