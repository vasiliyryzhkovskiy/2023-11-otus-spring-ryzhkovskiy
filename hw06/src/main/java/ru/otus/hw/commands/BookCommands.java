package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "booksall")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bookid")
    public String findBookById(long id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %d not found".formatted(id));
    }

    /**
     * Добавление книги
     * Пример команды bookinsert newBook 1 1
     * Пример команды bookinsert newBook 2 2
     * Пример команды bookinsert newBook 2 3
     * Пример команды bookinsert newBook 3 2
     * Пример команды bookinsert newBook 3 3
     */
    @ShellMethod(value = "Insert new book", key = "bookinsert")
    public String insertBook(String title, long authorId, long genreId) {
        BookDto book = bookService.insert(title, authorId, genreId);
        return bookConverter.bookToString(book);
    }

    /**
     * Изменение книги
     * Пример команды bookup 4 editedBook 1 3
     */
    @ShellMethod(value = "Update book", key = "bookup")
    public String updateBook(long id, String title, long authorId, long genreId) {
        BookDto book = bookService.update(id, title, authorId, genreId);
        return bookConverter.bookToString(book);
    }

    /**
     * Удаление книги
     * Пример команды bookdel 5
     */
    @ShellMethod(value = "Delete book by id", key = "bookdel")
    public void deleteBook(long id) {
        bookService.deleteById(id);
    }
}
