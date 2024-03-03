package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.interfaces.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    @Override
    public Comment findById(String id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }


    @Transactional
    @Override
    public Comment insert(String content, String bookId) {
        return save(null, content, bookId);
    }

    @Transactional
    @Override
    public Comment update(String id, String content, String bookId) {
        return save(id, content, bookId);
    }


    @Transactional
    @Override
    public void deleteById(String id) {
        findById(id);
        commentRepository.deleteById(id);
    }

    private Comment save(String id, String content, String bookId) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));
        Comment comment = new Comment(id, content, book);
        return commentRepository.save(comment);
    }
}