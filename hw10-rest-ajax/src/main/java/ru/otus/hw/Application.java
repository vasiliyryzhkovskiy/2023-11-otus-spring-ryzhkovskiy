package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

//http://localhost:8080/authors
//http://localhost:8080/authors/edit/1
//http://localhost:8080/genres
//http://localhost:8080/books

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println("Приложение работает по адресу: http://localhost:8080");
    }
}




