package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadSleepingSessionsCountTest {
    private BadSleepingSessionsCount counter;

    @BeforeEach
    void setUp() {
        counter = new BadSleepingSessionsCount();
    }

    @Test
    void testNoBadSessions() throws SleepTrackerException {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.01.23 22:00;02.01.23 06:00;GOOD"),
                new SleepingSession("02.01.23 22:00;03.01.23 06:00;NORMAL")
        );
        SleepAnalysisResult result = counter.apply(sessions);
        assertEquals(0L, result.getValue());
    }

}
