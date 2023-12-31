package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.domain.Student;


@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private final Student student = new Student("Ivan", "Popov");

    @Mock
    private StudentService studentService;

    @Test
    @DisplayName("[Positive Test] Check method determineCurrentStudent")
    void positiveTest() {
        Mockito.when(studentService.determineCurrentStudent()).thenReturn(student);
        Student expectedStudent = new Student("Ivan", "Popov");
        Assertions.assertEquals(expectedStudent, studentService.determineCurrentStudent());
    }

    @Test
    @DisplayName("[Negative Test] Check method determineCurrentStudent")
    void negativeTest() {
        Mockito.when(studentService.determineCurrentStudent()).thenReturn(student);
        Student expectedStudent = new Student("Alexander", "Pushkin");
        Assertions.assertNotEquals(expectedStudent, studentService.determineCurrentStudent());
    }
}