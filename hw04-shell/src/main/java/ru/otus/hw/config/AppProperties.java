package ru.otus.hw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, IOConfig, LocaleConfig {
    private final int maxAttemps;

    private final int rightAnswersCountToPass;

    private final Locale locale;

    private final Map<Locale, String> fileNameByLocaleTag;

    public AppProperties(int maxAttemps,
                         int rightAnswersCountToPass,
                         Locale locale,
                         Map<Locale, String> fileNameByLocaleTag) {
        this.maxAttemps = maxAttemps;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = locale;
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(getLocale());
    }

    @Override
    public int getMaxAttemps() {
        return maxAttemps;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}