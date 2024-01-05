package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.Student;

import static org.mockito.BDDMockito.given;

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
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn("VASYA");
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn("PUPKIN");
        Assertions.assertNotNull(studentService.determineCurrentStudent());
    }

    @Test
    @DisplayName("[Positive Test] Check method determineCurrentStudent")
    void positiveTest() {
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn("VASYA");
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn("PUPKIN");
        Student expectedStudent = new Student("VASYA", "PUPKIN");
        Assertions.assertEquals(expectedStudent, studentService.determineCurrentStudent());
    }

    @Test
    @DisplayName("[Negative Test] Check method determineCurrentStudent")
    void negativeTest() {
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn("VASYA");
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn("PUPKIN");
        Student expectedStudent = new Student("SANYA", "PUPKIN");
        Assertions.assertNotEquals(expectedStudent, studentService.determineCurrentStudent());
    }


    @Test
    @DisplayName("[Positive Test] Check method determineCurrentStudent with null values from ioService")
    void positiveNullTest() {
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.first.name"))).willReturn(null);
        given(ioService.readStringWithPromptLocalized(ArgumentMatchers.eq("StudentService.input.last.name"))).willReturn(null);

        Student expectedStudent = new Student(null, null);
        Student actual = studentService.determineCurrentStudent();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedStudent, actual);
    }

}