package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private long id;

    private String fullName;

    public AuthorDto(Author author) {
        this(author.getId(), author.getFullName());
    }
}
