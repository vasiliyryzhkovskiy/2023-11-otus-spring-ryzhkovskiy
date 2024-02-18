package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    /**
     * Поиск комментария по идентификатору
     * Пример команды
     * commentid 1
     * где 1 это идентификатор
     */
    @ShellMethod(value = "Find comment by id", key = "commentid")
    public String findCommentById(long id) {

        return commentService.findById(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    /**
     * Добавление комментария
     * Пример команды
     * commentinsert newComment_1 1
     * commentinsert newComment_2 2
     * commentinsert newComment_3 3
     * где 1 это идентификатор КНИГИ
     */
    @ShellMethod(value = "Insert new comment", key = "commentinsert")
    public String insertComment(String text, long bookId) {
        CommentDto comment = commentService.create(text, bookId);
        return commentConverter.commentToString(comment);
    }

    /**
     * Обновления комментария
     * Пример команды commentup 1 updateComment
     * где 1 это идентификатор КОММЕНТАРИЯ
     */
    @ShellMethod(value = "Update comment by id with text", key = "commentup")
    public String updateComment(long id, String text) {
        CommentDto comment = commentService.update(id, text);
        return commentConverter.commentToString(comment);
    }


    /**
     * Удаление комментария
     * Пример команды commentdel 1
     * где 1 это идентификатор КОММЕНТАРИЯ
     */
    @ShellMethod(value = "Delete comment by id", key = "commentdel")
    public void deleteComment(long id) {
        commentService.deleteById(id);
    }


    /**
     * Комментарии по ИД книги
     * Пример команды commentbybookid 1
     * где 1 это идентификатор КНИГИ
     */
    @ShellMethod(value = "Find comment by book id", key = "commentbybookid")
    public String findCommentByBookId(long id) {
        return commentService.findByBookId(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}