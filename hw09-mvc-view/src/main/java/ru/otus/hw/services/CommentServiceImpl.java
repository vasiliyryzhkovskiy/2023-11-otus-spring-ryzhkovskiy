package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(CommentDto::new);
    }

    @Override
    @Transactional
    public List<CommentDto> findByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(CommentDto::new).toList();
    }

    @Override
    @Transactional
    public CommentDto create(String text, long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        Comment newComment = new Comment(0, text, book);
        Comment savedComment = commentRepository.save(newComment);
        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found.".formatted(id)));
        comment.setText(text);
        Comment savedComment = commentRepository.save(comment);
        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
