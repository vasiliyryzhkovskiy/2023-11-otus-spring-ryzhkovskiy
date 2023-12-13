package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoCheckFindAllTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
    }

    @Test
    @DisplayName("Check method FindAll")
    void findAll() {
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider);
        Assertions.assertNotNull(dao.findAll());
    }
}