package ru.otus.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity // Указывает, что данный класс является сущностью
@Table(name = "authors") // Задает имя таблицы, на которую будет отображаться сущность
public class Author {
    @Id // Позволяет указать какое поле является идентификатором
    private long id;

    @Column(name = "full_name")
    private String fullName;
}
