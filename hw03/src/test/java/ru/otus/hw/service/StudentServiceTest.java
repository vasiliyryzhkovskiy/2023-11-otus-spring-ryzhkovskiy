package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.Student;

import static org.mockito.BDDMockito.given;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = StudentServiceImpl.class)
public class StudentServiceTest {
    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn("VASYA");
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn("PUPKIN");
    }

    @Test
    @DisplayName("[Positive Test] Check for Not Null")
    void nuNullTest() {
        Assertions.assertNotNull(studentService.determineCurrentStudent());
    }

    @Test
    @DisplayName("[Positive Test] Check method determineCurrentStudent")
    void positiveTest() {
        Student expectedStudent = new Student("VASYA", "PUPKIN");
        Assertions.assertEquals(expectedStudent, studentService.determineCurrentStudent());
    }

    @Test
    @DisplayName("[Negative Test] Check method determineCurrentStudent")
    void negativeTest() {
        Student expectedStudent = new Student("SANYA", "PUPKIN");
        Assertions.assertNotEquals(expectedStudent, studentService.determineCurrentStudent());
    }
}