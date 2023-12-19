package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.TestRunnerService;

@Configuration
@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args) {

        //Создать контекст на основе Annotation/Java конфигурирования
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.otus.hw");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}