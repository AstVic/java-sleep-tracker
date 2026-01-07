package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SleeplessNightsCount implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions == null || sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей: ", 0L);
        }

        LocalDateTime firstStart = sleepingSessions.get(0).getSessionStart();
        LocalDateTime lastEnd = sleepingSessions.get(sleepingSessions.size() - 1).getSessionFinish();

        LocalDate firstNight = getNightForSessionStart(firstStart);
        LocalDate lastNight = getNightForSessionEnd(lastEnd);

        if (firstNight.isAfter(lastNight)) {
            return new SleepAnalysisResult("Количество бессонных ночей: ", 0L);
        }

        Set<LocalDate> nightsWithSleep = new HashSet<>();

        sleepingSessions.forEach(session -> {
            LocalDate startNight = session.getSessionStart().toLocalDate();
            if (intersectsNight(session, startNight) &&
                    !startNight.isBefore(firstNight) &&
                    !startNight.isAfter(lastNight)) {
                nightsWithSleep.add(startNight);
            }

            LocalDate endNight = session.getSessionFinish().toLocalDate();
            if (!endNight.equals(startNight) &&
                    intersectsNight(session, endNight) &&
                    !endNight.isBefore(firstNight) &&
                    !endNight.isAfter(lastNight)) {
                nightsWithSleep.add(endNight);
            }
        });

        long totalNights = ChronoUnit.DAYS.between(firstNight, lastNight) + 1;

        long sleeplessNights = totalNights - nightsWithSleep.size();

        return new SleepAnalysisResult("Количество бессонных ночей: ", sleeplessNights);
    }

    private LocalDate getNightForSessionStart(LocalDateTime sessionStart) {
        return sessionStart.toLocalTime().isBefore(LocalTime.NOON)
                ? sessionStart.toLocalDate().minusDays(1)
                : sessionStart.toLocalDate();
    }


    private LocalDate getNightForSessionEnd(LocalDateTime sessionEnd) {
        return sessionEnd.toLocalTime().isBefore(LocalTime.NOON)
                ? sessionEnd.toLocalDate().minusDays(1)
                : sessionEnd.toLocalDate();
    }


    private boolean intersectsNight(SleepingSession session, LocalDate night) {
        LocalDateTime nightStart = night.atStartOfDay();
        LocalDateTime nightEnd = night.atTime(6, 0);

        LocalDateTime sessionStart = session.getSessionStart();
        LocalDateTime sessionEnd = session.getSessionFinish();

        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
    }
}