//package ru.otus.hw.repositories;
//
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.stereotype.Repository;
//import ru.otus.hw.exceptions.EntityNotFoundException;
//import ru.otus.hw.models.Author;
//import ru.otus.hw.models.Book;
//import ru.otus.hw.models.Genre;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class JdbcBookRepository implements BookRepository {
//
//    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
//
//    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
//        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
//    }
//
//    @Override
//    public Optional<Book> findById(long id) {
//        Map<String, Object> params = Collections.singletonMap("id", id);
//
//        List<Book> result = namedParameterJdbcOperations.query("""
//                        select
//                        books.id books_id,
//                        books.title books_title,
//                        books.author_id books_author_id,
//                        books.genre_id books_genre_id,
//                        authors.id authors_id,
//                        authors.full_name authors_full_name,
//                        genres.id genres_id,
//                        genres.name genres_name
//                        from books
//                        join authors on books.author_id = authors.id
//                        join genres on books.genre_id = genres.id
//                        where books.id = :id
//                        """,
//                params,
//                new JdbcBookRepository.BookRowMapper());
//        return Optional.of(result.getFirst());
//
//    }
//
//    @Override
//    public List<Book> findAll() {
//        return namedParameterJdbcOperations.query("""
//                        select
//                        books.id books_id,
//                        books.title books_title,
//                        books.author_id books_author_id,
//                        books.genre_id books_genre_id,
//                        authors.id authors_id,
//                        authors.full_name authors_full_name,
//                        genres.id genres_id,
//                        genres.name genres_name
//                        from books
//                        join authors on books.author_id = authors.id
//                        join genres on books.genre_id = genres.id""",
//                new JdbcBookRepository.BookRowMapper());
//    }
//
//    @Override
//    public Book save(Book book) {
//        if (book.getId() == 0) {
//            return insert(book);
//        }
//        return update(book);
//    }
//
//    @Override
//    public void deleteById(long id) {
//        Map<String, Object> params = Collections.singletonMap("id", id);
//        namedParameterJdbcOperations.update("delete from books where id = :id", params);
//    }
//
//    private Book insert(Book book) {
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("title", book.getTitle());
//        mapSqlParameterSource.addValue("author_id", book.getAuthor().getId());
//        mapSqlParameterSource.addValue("genre_id", book.getGenre().getId());
//
//        namedParameterJdbcOperations.update("""
//                        insert into books (title, author_id, genre_id)
//                        values(:title, :author_id, :genre_id)
//                        """,
//                mapSqlParameterSource, keyHolder, new String[]{"id"});
//
//        //noinspection DataFlowIssue
//        book.setId(keyHolder.getKeyAs(Long.class));
//        return book;
//    }
//
//    private Book update(Book book) {
//        Map<String, Object> params = Map.of("id", book.getId(),
//                "title", book.getTitle(),
//                "author_id", book.getAuthor().getId(),
//                "genre_id", book.getGenre().getId());
//
//        int count = namedParameterJdbcOperations.update("""
//                update books
//                set title = :title,
//                    author_id = :author_id,
//                    genre_id = :genre_id
//                where id = :id""", params);
//
//        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
//        if (count == 0) {
//            throw new EntityNotFoundException("Book with id = %s was not updated".formatted(book.getId()));
//        }
//        return book;
//    }
//
//    private static class BookRowMapper implements RowMapper<Book> {
//        @Override
//        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
//            long authorId = rs.getLong("author_id");
//            String authorName = rs.getString("authors_full_name");
//
//            long genreId = rs.getLong("genre_id");
//            String genreName = rs.getString("genres_name");
//
//            long bookId = rs.getLong("books_id");
//            String bookTitle = rs.getString("books_title");
//
//            return new Book(bookId, bookTitle, new Author(authorId, authorName), new Genre(genreId, genreName));
//        }
//    }
//}