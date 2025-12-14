package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AverageSessionTimeTest {
    private AverageSessionTime calculator;

    @BeforeEach
    void setUp() {
        calculator = new AverageSessionTime();
    }

    @Test
    void testEmptyList() {
        List<SleepingSession> empty = Collections.emptyList();
        SleepAnalysisResult result = calculator.apply(empty);
        assertEquals(0L, result.getValue());
    }

    @Test
    void testSingleSession() throws SleepTrackerException {
        SleepingSession session = new SleepingSession("01.01.23 22:00;02.01.23 06:00;GOOD");
        List<SleepingSession> sessions = List.of(session);
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals(480L, result.getValue());
    }

}