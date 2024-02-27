package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.interfaces.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    /**
     * Получить комментарий по id
     * Пример команды
     * commentid id
     * где id это идентификатор КОММЕНТАРИЯ
     */
    @ShellMethod(value = "Find comment by id", key = "commentid")
    public String findCommentById(String id) {
        return commentService.findById(id).toString();
//                .map(commentConverter::commentToString)
//                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    /**
     * Добавление комментария
     * Пример команды
     * commentinsert newComment bookId
     * где bookId это идентификатор КНИГИ
     */
    @ShellMethod(value = "Insert new comment", key = "commentinsert")
    public String insertComment(String text, String bookId) {
        Comment comment = commentService.insert(text, bookId);
        return commentConverter.commentToString(comment);
    }

    /**
     * Обновления комментария
     * Пример команды
     * commentup id updateComment bookId
     * где id это идентификатор КОММЕНТАРИЯ
     * где bookId это идентификатор КНИГИ
     */
    @ShellMethod(value = "Update comment by id with text", key = "commentup")
    public String updateComment(String id, String text, String bookId) {
        Comment comment = commentService.update(id, text, bookId);
        return commentConverter.commentToString(comment);
    }

    /**
     * Комментарии по ИД книги
     * Пример команды
     * commentbybookid id
     * где id это идентификатор КНИГИ
     */
    @ShellMethod(value = "Find comment by book id", key = "commentbybookid")
    public String findCommentByBookId(String id) {
        return commentService.findAllByBookId(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    /**
     * Удаление комментария
     * Пример команды
     * commentdel id
     * где id это идентификатор КОММЕНТАРИЯ
     */
    @ShellMethod(value = "Delete comment by id", key = "commentdel")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }
}