package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование репозитория комментариев")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final List<Author> listAuthors = List.of(
            new Author("id_1", "Author_1"),
            new Author("id_2", "Author_2"),
            new Author("id_3", "Author_3"));

    private final List<Genre> listGenres = List.of(
            new Genre("id_1", "Genre_1"),
            new Genre("id_2", "Genre_2"),
            new Genre("id_3", "Genre_3"));

    private final List<Book> listBooks = List.of(
            new Book("id_1", "Book_1", listAuthors.get(0), listGenres.get(0)),
            new Book("id_2", "Book_2", listAuthors.get(1), listGenres.get(1)),
            new Book("id_3", "Book_3", listAuthors.get(2), listGenres.get(2)));

    private final List<Comment> listComments = List.of(
            new Comment("id_1", "Comment_1", listBooks.get(0)),
            new Comment("id_2", "Comment_2", listBooks.get(1)),
            new Comment("id_3", "Comment_3", listBooks.get(2)));

    @BeforeEach
    void beforeEach() {
        mongoTemplate.dropCollection(Comment.class);
        listComments.forEach(comment -> mongoTemplate.save(comment));
    }

    @Test
    @DisplayName("Добавление нового комментария")
    void saveNewComment() {
        Comment comment = new Comment("id_4", "Comment_4", listBooks.getFirst());
        Comment savedComment = commentRepository.save(new Comment("id_4", "Comment_4", listBooks.getFirst()));

        Assertions.assertAll("Проверка корректности добавления комментария", () -> {
            Assertions.assertNotNull(savedComment);
            Assertions.assertEquals(savedComment, comment);
            Assertions.assertEquals(savedComment.getText(), comment.getText());
            Assertions.assertEquals(savedComment.getId(), comment.getId());
        });
    }

    @Test
    @DisplayName("Удаление нового комментария")
    void deleteNewComment() {
        Comment comment = new Comment("id_5", "Comment_5", listBooks.getFirst());
        Comment savedComment = commentRepository.save(new Comment("id_5", "Comment_5", listBooks.getFirst()));

        Assertions.assertAll("Проверка корректности добавления комментария", () -> {
            Assertions.assertNotNull(savedComment);
            Assertions.assertEquals(savedComment, comment);
            Assertions.assertEquals(savedComment.getText(), comment.getText());
            Assertions.assertEquals(savedComment.getId(), comment.getId());
        });

        commentRepository.deleteById(comment.getId());

        Assertions.assertAll("Проверка корректности удаления комментария", () -> {
            Comment find = mongoTemplate.findById(savedComment.getId(), Comment.class);
            Assertions.assertNull(find);
        });
    }

    @Test
    @DisplayName("Поиск первого комментария")
    void findFirstComment() {
        Optional<Comment> repositoryComment = commentRepository.findById(listComments.getFirst().getId());
        Comment expectedComment = listComments.getFirst();

        repositoryComment.ifPresentOrElse(comment -> Assertions.assertAll("Проверка корректности поиска комментария", () -> {
            Assertions.assertEquals(comment.getId(), expectedComment.getId(), "Проверка ID комментария");
            Assertions.assertEquals(comment.getText(), expectedComment.getText(), "Проверка текста комментария");
        }), () -> {
            throw new EntityNotFoundException("Такой комментарий отстутствует");
        });
    }
}