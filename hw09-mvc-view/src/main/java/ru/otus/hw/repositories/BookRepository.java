package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b")
    @EntityGraph(value = "books-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllFetchAuthorsAndGenres();

    ;
}