package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinSessionTimeTest {

    @Test
    void shouldFindMinimalSession() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 22:00;02.10.25 06:00;GOOD"),   // 480
                new SleepingSession("03.10.25 14:00;03.10.25 15:00;NORMAL") // 60
        );

        SleepAnalysisResult result =
                new MinSessionTime().apply(sessions);

        assertEquals(60L, result.getValue());
    }

    @Test
    void shouldReturnNullForEmptyList() {
        assertNotNull(new MinSessionTime().apply(List.of()));
    }
}
