package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings

        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

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

        } catch (URISyntaxException | IOException e) {
            throw new QuestionReadException("Error file reading", e);
        }

    }
}
