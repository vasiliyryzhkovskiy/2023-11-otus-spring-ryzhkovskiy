package ru.otus.hw.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Setter
//@EnableConfigurationProperties(AppProperties.class)
//@PropertySource("classpath:application.properties")
//@PropertySource("application")
@ConfigurationProperties(prefix = "test")
//@Component
public class AppProperties implements TestConfig, TestFileNameProvider, IOConfig {
    private final int maxAttemps;

    private final int rightAnswersCountToPass;

    private final String testFileName;

//    public AppProperties(@Value("${test.max_attemps}") int maxAttemps,
//                         @Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
//                         @Value("${test.fileName}") String testFileName) {
//        this.maxAttemps = maxAttemps;
//        this.rightAnswersCountToPass = rightAnswersCountToPass;
//        this.testFileName = testFileName;
//    }

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