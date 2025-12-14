package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaxSessionTimeTest {

    @Test
    void shouldFindMaximumSession() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 23:00;02.10.25 07:00;GOOD"), // 480
                new SleepingSession("02.10.25 22:00;03.10.25 08:00;GOOD")  // 600
        );

        SleepAnalysisResult result =
                new MaxSessionTime().apply(sessions);

        assertEquals(600L, result.getValue());
    }

    @Test
    void shouldReturnNullForEmptyList() {
        assertNotNull(new MaxSessionTime().apply(List.of()));
    }
}

