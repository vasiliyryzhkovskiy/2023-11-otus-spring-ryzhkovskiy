package ru.otus.hw.services.interfaces;


import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
