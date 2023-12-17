package ru.otus.hw.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class TestRunnerServiceImpl implements CommandLineRunner {
    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    public TestRunnerServiceImpl(TestService testService, StudentService studentService, ResultService resultService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @Override
    public void run(String... args) throws Exception {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}