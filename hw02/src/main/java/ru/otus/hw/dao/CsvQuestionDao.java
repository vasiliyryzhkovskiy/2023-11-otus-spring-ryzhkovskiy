package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    public CsvQuestionDao(TestFileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    @Override
    public List<Question> findAll() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
            if (null != is) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                            .withSkipLines(1)
                            .withSeparator(';')
                            .withType(QuestionDto.class)
                            .build();

                    return csvToBean.parse().stream().map(QuestionDto::toDomainObject).toList();
                }
            } else {
                throw new NullPointerException("InputStream is null. Check resource file !");
            }

        } catch (Throwable e) {
            throw new QuestionReadException("Error file reading", e);
        }
    }
}
