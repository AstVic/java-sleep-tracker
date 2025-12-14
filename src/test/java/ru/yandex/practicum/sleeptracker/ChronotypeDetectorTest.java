package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChronotypeDetectorTest {

    @Test
    void shouldDetectOwl() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 01:00;01.10.25 10:00;GOOD"),
                new SleepingSession("02.10.25 01:30;02.10.25 09:30;GOOD")
        );

        SleepAnalysisResult result =
                new ChronotypeDetector().apply(sessions);

        assertEquals("Сова", result.getValue());
    }

    @Test
    void shouldReturnPigeonOnTie() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 21:00;02.10.25 06:00;GOOD"), // жаворонок
                new SleepingSession("02.10.25 01:00;02.10.25 10:00;GOOD")  // сова
        );

        SleepAnalysisResult result =
                new ChronotypeDetector().apply(sessions);

        assertEquals("Голубь", result.getValue());
    }
}

