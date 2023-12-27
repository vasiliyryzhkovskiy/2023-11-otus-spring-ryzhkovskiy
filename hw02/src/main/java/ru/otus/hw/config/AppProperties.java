package ru.otus.hw.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Component
public class AppProperties implements TestConfig, TestFileNameProvider, IOConfig {
    private int maxAttemps;

    private int rightAnswersCountToPass;

    private String testFileName;

    public AppProperties(@Value("${test.max.attemps}") int maxAttemps,
                         @Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
                         @Value("${test.fileName}") String testFileName) {
        this.maxAttemps = maxAttemps;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public int getMaxAttemps() {
        return maxAttemps;
    }
}
