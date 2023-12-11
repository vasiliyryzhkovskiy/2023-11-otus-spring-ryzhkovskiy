package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {
    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    public TestRunnerServiceImpl(@Autowired TestService testService, @Autowired StudentService studentService, @Autowired ResultService resultService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}