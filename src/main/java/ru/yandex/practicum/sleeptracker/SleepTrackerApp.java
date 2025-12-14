package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static List<Function<List<SleepingSession>, SleepAnalysisResult>> functions;
    private static List<SleepingSession> sleepingSessions;
    private static SleepingSessionsLoader sleepingSessionsLoader;

    public static void main(String[] args) throws IOException {

        File file = new File("src\\main\\resources\\sleep_log.txt");
        sleepingSessionsLoader = new SleepingSessionsLoader(file);

        functions = new ArrayList<>();
        functions.add(new SessionsCountByPeriod());
        functions.add(new MinSessionTime());
        functions.add(new MaxSessionTime());
        functions.add(new AverageSessionTime());
        functions.add(new BadSleepingSessionsCount());
        functions.add(new SleeplessNightsCount());
        functions.add(new ChronotypeDetector());

        try {
            sleepingSessions = sleepingSessionsLoader.getSleepingSessions();
            functions.stream()
                    .forEach(function -> {
                        System.out.println(function.apply(sleepingSessions));
                    });

        } catch (IOException e) {
            System.out.println("Ошибка открытия файла");
        } catch (SleepTrackerException e) {
            System.out.println(e.getMessage());
            ;
        }

    }
}