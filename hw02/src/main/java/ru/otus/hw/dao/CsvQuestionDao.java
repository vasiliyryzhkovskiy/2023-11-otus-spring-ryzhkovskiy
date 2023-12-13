package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    public CsvQuestionDao(@Autowired TestFileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    @Override
    public List<Question> findAll() {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileNameProvider.getTestFileName()).toURI());

            try (Reader reader = Files.newBufferedReader(path)) {
                CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                        .withSkipLines(1)
                        .withSeparator(';')
                        .withType(QuestionDto.class)
                        .build();

                return csvToBean.parse().stream().map(QuestionDto::toDomainObject).toList();
            }

        } catch (Throwable e) {
            throw new QuestionReadException("Error file reading", e);
        }
    }
}
