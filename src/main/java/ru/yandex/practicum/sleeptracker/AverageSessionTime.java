package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionTime implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long sumOfDurations = sleepingSessions.stream()
                .map(SleepingSession::getDuration)
                .map(Duration::toMinutes)
                .reduce(Long::sum)
                .orElse(0L);

        long countOfSessions = (long) sleepingSessions.size();

        return new SleepAnalysisResult("Средняя длина сессии сна (минуты): ",
                (sumOfDurations == 0 || countOfSessions == 0) ? 0L : (long) sumOfDurations / countOfSessions);
    }
}
