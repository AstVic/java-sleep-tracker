package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    @Test
    void shouldLoadAndAnalyzeSleepData() throws IOException {
        File tempFile = createTestSleepLogFile();

        assertDoesNotThrow(() -> {
            String[] args = {tempFile.getAbsolutePath()};
            SleepTrackerApp.main(args);
        });
        tempFile.delete();
    }

    @Test
    void shouldNotThrowExceptionForInvalidFile() {
        File invalidFile = new File("non_existent_file.txt");
        assertDoesNotThrow(() -> {
            String[] args = {invalidFile.getAbsolutePath()};
            SleepTrackerApp.main(args);
        });
    }

    @Test
    void shouldNotThrowExceptionForInvalidData() throws IOException {
        File invalidDataFile = createInvalidDataSleepLogFile();
        assertDoesNotThrow(() -> {
            String[] args = {invalidDataFile.getAbsolutePath()};
            SleepTrackerApp.main(args);
        });

        // Удаление временного файла после теста
        invalidDataFile.delete();
    }

    private File createTestSleepLogFile() throws IOException {
        File tempFile = File.createTempFile("sleep_log_test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("01.10.25 22:15;02.10.25 08:00;GOOD\n");
            writer.write("02.10.25 23:00;03.10.25 08:00;NORMAL\n");
            writer.write("03.10.25 14:30;03.10.25 15:20;NORMAL\n");
            writer.write("03.10.25 23:30;04.10.25 06:20;BAD\n");
        }
        return tempFile;
    }

    private File createInvalidDataSleepLogFile() throws IOException {
        File tempFile = File.createTempFile("invalid_sleep_log_test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("01.10.25 22:15;INVALID DATE;GOOD\n"); // Некорректная дата
        }
        return tempFile;
    }
}
