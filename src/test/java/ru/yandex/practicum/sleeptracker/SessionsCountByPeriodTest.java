package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionsCountByPeriodTest {

    @Test
    void shouldCountAllSessions() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 22:00;02.10.25 06:00;GOOD"),
                new SleepingSession("02.10.25 14:00;02.10.25 15:00;NORMAL")
        );

        SleepAnalysisResult result =
                new SessionsCountByPeriod().apply(sessions);

        assertEquals(2L, result.getValue());
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        SleepAnalysisResult result =
                new SessionsCountByPeriod().apply(List.of());

        assertEquals(0L, result.getValue());
    }
}

