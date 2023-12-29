package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final LocalizedIOService ioService;

    public StudentServiceImpl(LocalizedIOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPromptLocalized("StudentService.input.first.name");
        var lastName = ioService.readStringWithPromptLocalized("StudentService.input.last.name");
        return new Student(firstName, lastName);
    }
}
