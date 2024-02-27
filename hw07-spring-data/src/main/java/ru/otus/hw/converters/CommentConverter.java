package ru.otus.hw.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;

@AllArgsConstructor
@Component
public class CommentConverter {

    public String commentToString(CommentDto comment) {
        return "Id: %d, text %s"
                .formatted(comment.getId(),
                        comment.getText());
    }
}