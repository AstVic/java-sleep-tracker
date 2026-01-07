package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleeplessNightsCountTest {

    @Test
    void sleeplessNightIfOnlyDaySleep() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 07:00;01.10.25 11:00;NORMAL")
        );

        SleepAnalysisResult result = new SleeplessNightsCount().apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldRespectNoonRuleForFirstNight() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 13:00;01.10.25 14:00;NORMAL")
        );

        SleepAnalysisResult result = new SleeplessNightsCount().apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testEmptyList() {
        SleepAnalysisResult result = new SleeplessNightsCount().apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testSessionEndingExactlyAtMidnight() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 22:00;02.10.25 00:00;GOOD")
        );

        SleepAnalysisResult result = new SleeplessNightsCount().apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testSessionStartingExactlyAt6AM() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 06:00;01.10.25 10:00;GOOD")
        );

        SleepAnalysisResult result = new SleeplessNightsCount().apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testComplexScenarioWithMultipleNights() throws Exception {
        List<SleepingSession> sessions = List.of(
                new SleepingSession("01.10.25 07:00;01.10.25 11:00;NORMAL"),
                new SleepingSession("02.10.25 22:00;03.10.25 06:00;GOOD"),
                new SleepingSession("04.10.25 14:00;04.10.25 15:00;BAD"),
                new SleepingSession("05.10.25 01:00;05.10.25 05:00;NORMAL")
        );

        SleepAnalysisResult result = new SleeplessNightsCount().apply(sessions);
        assertEquals(4L, result.getValue());
    }
}