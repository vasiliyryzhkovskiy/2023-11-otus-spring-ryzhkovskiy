package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    private String text;

    private BookDto book;

    public CommentDto(Comment comment) {
        this(comment.getId(), comment.getText(), new BookDto());
    }

}
