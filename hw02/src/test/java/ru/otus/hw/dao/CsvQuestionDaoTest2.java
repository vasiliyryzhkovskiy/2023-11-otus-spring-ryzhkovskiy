package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

/**
 *
 */
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest2 {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
    }

    @Test
    void findAll() {

        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider);

        Assertions.assertNotNull(dao.findAll());

//         Assertions.assertThrowsExactly( QuestionReadException.class, () -> dao.findAll());

    }
}