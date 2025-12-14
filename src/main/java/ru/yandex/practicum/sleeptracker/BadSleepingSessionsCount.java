package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepingSessionsCount implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult("Количество плохих сессий снов: ", sleepingSessions.stream()
                .filter(sleepingSession -> sleepingSession.getRank().equals(SleepingRank.BAD))
                .count());
    }
}
