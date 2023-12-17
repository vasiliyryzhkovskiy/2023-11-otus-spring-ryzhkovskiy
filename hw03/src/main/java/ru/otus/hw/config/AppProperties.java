package ru.otus.hw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, IOConfig {
    private final int maxAttemps;

    private final int rightAnswersCountToPass;

    private final String testFileName;


    @ConstructorBinding
    public AppProperties(int maxAttemps,
                         int rightAnswersCountToPass,
                         String testFileName) {
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
