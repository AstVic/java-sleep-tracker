package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MaxSessionTime implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.stream()
                .map(SleepingSession::getDuration)
                .map(Duration::toMinutes)
                .max(Comparator.comparing(duration -> duration))
                .map(duration ->
                        new SleepAnalysisResult("Максимальная продолжительность сессии сна (минуты): ", duration))
                .orElse(new SleepAnalysisResult("Максимальная продолжительность сесси сна (минуты): ", 0L));
    }
}
