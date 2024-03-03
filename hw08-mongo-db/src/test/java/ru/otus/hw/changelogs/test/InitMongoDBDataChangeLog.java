package ru.otus.hw.changelogs.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@ChangeLog
public class InitMongoDBDataChangeLog {
    private List<Author> authors;

    private List<Genre> genres;

    private List<Book> books;

    @ChangeSet(order = "001", id = "dropDB", author = "vasiliy", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "vasiliy", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        Author author1 = repository.save(new Author("id_1", "Author_1"));
        Author author2 = repository.save(new Author("id_2", "Author_2"));
        Author author3 = repository.save(new Author("id_3", "Author_3"));
        authors = List.of(author1, author2, author3);
    }

    @ChangeSet(order = "003", id = "initGenres", author = "vasiliy", runAlways = true)
    public void initGenres(GenreRepository repository) {
        Genre genre1 = repository.save(new Genre("id_1", "Genre_1"));
        Genre genre2 = repository.save(new Genre("id_2", "Genre_2"));
        Genre genre3 = repository.save(new Genre("id_3", "Genre_3"));
        genres = List.of(genre1, genre2, genre3);
    }

    @ChangeSet(order = "004", id = "initBooks", author = "vasiliy", runAlways = true)
    public void initBooks(BookRepository repository) {
        Book book1 = repository.save(new Book("id_1", "BookTitle_1", authors.get(0), genres.get(0)));
        Book book2 = repository.save(new Book("id_2", "BookTitle_2", authors.get(1), genres.get(1)));
        Book book3 = repository.save(new Book("id_3", "BookTitle_3", authors.get(2), genres.get(2)));
        books = List.of(book1, book2, book3);
    }

    @ChangeSet(order = "005", id = "initComments", author = "vasiliy", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment("id_1", "Comment_1", books.get(0)));
        repository.save(new Comment("id_2", "Comment_2", books.get(1)));
        repository.save(new Comment("id_3", "Comment_3", books.get(2)));
    }
}