package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class ApplicationConfig {

    @Bean
    public TestRightAnswersCountToPass testRightAnswersCountToPass(@Value("${test.rightAnswersCountToPass}") int count) {
        return new TestRightAnswersCountToPass(count);
    }

    @Bean
    public TestFileName testFileName(@Value("${test.testFileName}") String fileName) {
        return new TestFileName(fileName);
    }

    @Bean
    public TestMaxAttemps testMaxAttemps(@Value("${test.maxAttemps}") int maxAttemps) {
        return new TestMaxAttemps(maxAttemps);
    }

}