package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

    List<Comment> findByBook(long bookId);

    void deleteById(long id);

    Comment insert(String text, long bookId);

    Comment update(long id, String text);
}
