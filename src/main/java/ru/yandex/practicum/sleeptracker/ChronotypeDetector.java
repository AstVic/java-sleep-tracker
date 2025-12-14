package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeDetector implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя: ", "Не определен");
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSession)
                .toList();

        if (nightSessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя: ", "Не определен");
        }

        Map<Chronotype, Long> counts = nightSessions.stream()
                .map(this::detectChronotype)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long pigeons = counts.getOrDefault(Chronotype.PIGEON, 0L);

        String result;
        if (owls > larks && owls > pigeons) {
            result = "Сова";
        } else if (larks > owls && larks > pigeons) {
            result = "Жаворонок";
        } else {
            result = "Голубь";
        }

        return new SleepAnalysisResult("Хронотип пользователя: ", result);
    }

    private boolean isNightSession(SleepingSession session) {
        return session.getSessionStart().toLocalTime().isBefore(LocalTime.of(6, 0))
                || session.getSessionFinish().toLocalTime().isAfter(LocalTime.MIDNIGHT);
    }

    private Chronotype detectChronotype(SleepingSession session) {
        LocalTime sleep = session.getSessionStart().toLocalTime();
        LocalTime wake = session.getSessionFinish().toLocalTime();

        if ((sleep.isAfter(LocalTime.of(23, 0)) || sleep.isBefore(LocalTime.of(6, 0)))
                && wake.isAfter(LocalTime.of(9, 0))) {
            return Chronotype.OWL;
        }

        if (sleep.isBefore(LocalTime.of(22, 0)) && wake.isBefore(LocalTime.of(7, 0))) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }
}
