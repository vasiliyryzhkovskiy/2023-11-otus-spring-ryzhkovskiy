package ru.otus.hw.config;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Log
class AppPropertiesTest {

    private final int INIT_COUNT = 2;
    private final int CHECK_COUNT = 3;

    TestConfig testConfig;

    @BeforeEach
    void setUp() {
//        testConfig = new AppProperties();
        testConfig = new AppProperties(1, INIT_COUNT, "filename");
    }

    @Test
    @DisplayName("[Positive test] Check TestConfig method getRightAnswersCountToPass")
    void getRightAnswersCountToPassPositiveTest() {
        log.info("expected RightAnswersCountToPass " + INIT_COUNT);
        log.info("actual RightAnswersCountToPass " + testConfig.getRightAnswersCountToPass());

        Assertions.assertNotNull(testConfig);
        Assertions.assertEquals(INIT_COUNT, testConfig.getRightAnswersCountToPass(), "EXPECTED_COUNT equals for RightAnswersCountToPass");
    }

    @Test
    @DisplayName("[Negative test] Check TestConfig method getRightAnswersCountToPass")
    void getRightAnswersCountToPassNegativeTest() {
        log.info("expected RightAnswersCountToPass " + CHECK_COUNT);
        log.info("actual RightAnswersCountToPass " + testConfig.getRightAnswersCountToPass());

        Assertions.assertNotNull(testConfig);
        Assertions.assertNotEquals(CHECK_COUNT, testConfig.getRightAnswersCountToPass(), "EXPECTED_COUNT  NOT equals for RightAnswersCountToPass");
    }
}