package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.interfaces.BookService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;

    private final BookConverter bookConverter;

    /**
     * Получение ВСЕХ книг
     * Пример команды
     * booksall
     */
    @ShellMethod(value = "Find all books", key = "booksall")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    /**
     * Получение книги по id
     * Пример команды
     * bookid id
     */
    @ShellMethod(value = "Find book by id", key = "bookid")
    public String findBookById(String id) {
        return bookConverter.bookToString(bookService.findById(id));
    }

    /**
     * Добавление книги
     * Пример команды
     * bookinsert newBook authorId genreId
     */
    @ShellMethod(value = "Insert new book", key = "bookinsert")
    public String insertBook(String title, String authorId, String genreId) {
        BookDto book = bookService.insert(title, authorId, genreId);
        return bookConverter.bookToString(book);
    }

    /**
     * Изменение книги
     * Пример команды
     * bookup id editedBook authorId genreId
     */
    @ShellMethod(value = "Update book", key = "bookup")
    public String updateBook(String id, String title, String authorId, String genreId) {
        BookDto book = bookService.update(id, title, authorId, genreId);
        return bookConverter.bookToString(book);
    }

    /**
     * Удаление книги
     * Пример команды
     * bookdel id
     */
    @ShellMethod(value = "Delete book by id", key = "bookdel")
    public void deleteBook(String id) {
        bookService.deleteById(id);
    }
}