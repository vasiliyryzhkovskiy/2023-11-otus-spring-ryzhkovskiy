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
public class StudentServiceIONullTest {
    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn(null);
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn(null);
    }

    @Test
    @DisplayName("[Positive Test] Check method determineCurrentStudent with null values from ioService")
    void positiveTest() {
        Student expectedStudent = new Student(null, null);
        Student actual = studentService.determineCurrentStudent();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedStudent, actual);
    }
}