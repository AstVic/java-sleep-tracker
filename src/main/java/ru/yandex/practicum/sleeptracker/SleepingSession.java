package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private final LocalDateTime sessionStart;
    private final LocalDateTime sessionFinish;
    private final DateTimeFormatter formatter;
    private final Duration duration;
    private final SleepingRank rank;

    @Override
    public String toString() {
        return "SleepingSession{" +
                "sessionStart = " + sessionStart.format(formatter) +
                ", sessionFinish = " + sessionFinish.format(formatter) +
                ", rank = " + rank +
                '}';
    }

    public SleepingSession(String line) throws SleepTrackerException {
        String[] data = (line.split(";"));

        if (data.length != 3) {
            throw new SleepTrackerException("Некорректный формат строки");
        }

        formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        sessionStart = LocalDateTime.parse(data[0], formatter);
        sessionFinish = LocalDateTime.parse(data[1], formatter);

        if (!sessionFinish.isAfter(sessionStart)) {
            throw new SleepTrackerException("Время окончания раньше начала");
        }

        duration = Duration.between(sessionStart, sessionFinish);
        switch (data[2]) {
            case ("BAD"):
                rank = SleepingRank.BAD;
                break;
            case ("GOOD"):
                rank = SleepingRank.GOOD;
                break;
            case ("NORMAL"):
                rank = SleepingRank.NORMAL;
                break;
            default:
                throw new SleepTrackerException("Неправильная оценка сна");
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public SleepingRank getRank() {
        return rank;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public LocalDateTime getSessionFinish() {
        return sessionFinish;
    }

}
