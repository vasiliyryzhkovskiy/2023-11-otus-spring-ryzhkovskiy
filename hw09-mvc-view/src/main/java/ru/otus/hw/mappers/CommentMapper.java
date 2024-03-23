package ru.otus.hw.mappers;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        Long bookId = comment.getBook() != null ? comment.getBook().getId() : null;
        return new CommentDto(comment.getId(), comment.getText(), BookMapper.toBookDto(comment.getBook()));
    }
}
