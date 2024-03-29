package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@DisplayName("Тестирование репозитория комментариев")
@DataJpaTest(showSql = false)
@Import(JpaCommentRepository.class)
public class CommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Добавление нового комментария")
    void saveNewComment() {
        Book book = testEntityManager.find(Book.class, 1);
        Comment comment = repository.save(new Comment(0, "Comment_1_3", book));

        Assertions.assertAll("Проверка корректности добавления комментария", () -> {
            Comment find = testEntityManager.find(Comment.class, comment.getId());
            Assertions.assertEquals(find, comment);
            Assertions.assertEquals(find.getText(), comment.getText());
        });
    }

    @Test
    @DisplayName("Удаление нового комментария")
    void deleteNewComment() {
        Book book = testEntityManager.find(Book.class, 1);
        Comment comment = repository.save(new Comment(0, "Comment_1_3", book));

        Assertions.assertAll("Проверка корректности добавления комментария", () -> {
            Comment find = testEntityManager.find(Comment.class, comment.getId());
            Assertions.assertEquals(find, comment);
            Assertions.assertEquals(find.getText(), comment.getText());
        });

        repository.deleteById(comment.getId());

        Assertions.assertAll("Проверка корректности удаления книги", () -> {
            Comment find = testEntityManager.find(Comment.class, comment.getId());
            Assertions.assertNull(find);
        });
    }
}