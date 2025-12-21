package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SleepingSessionsLoader {
    private final File file;
    private List<SleepingSession> sleepingSessions;

    public SleepingSessionsLoader(File file) {
        this.file = file;
    }

    public List<SleepingSession> getSleepingSessions() throws IOException, SleepTrackerException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file.getAbsolutePath()), StandardCharsets.UTF_8))) {

            sleepingSessions = bufferedReader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(line -> {
                        try {
                            return new SleepingSession(line);
                        } catch (SleepTrackerException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + file.getAbsolutePath(), e);
        }
        return sleepingSessions;
    }
}