package ru.otus.hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;

//    private long id;

//    private String title;
//
//    private AuthorDto author;
//
//    private GenreDto genre;

    @NotBlank(message = "Book name has to be not blank")
    @Size(min = 2, max = 30, message = "Book name field should be between 2 and 30 characters")
    private String title;

    @Min(value = 1, message = "Author is not chosen")
    private Long authorId;

    @Min(value = 1, message = "Genre is not chosen")
    private Long genreId;

//    public BookDto(Book book) {
//        this(book.getId(), book.getTitle(), new AuthorDto(book.getAuthor()), new GenreDto(book.getGenre()));
//    }


//    public BookDto(Long id, String title, Long authorId, Long genreId) {
//        this.id = id;
//        this.title = title;
//        this.authorId = authorId;
//        this.genreId = genreId;
//    }
}
