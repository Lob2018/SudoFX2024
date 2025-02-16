package fr.softsf.sudokufx.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for working with date and time.
 */
@Component
public class MyDateTime {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Gets the current time formatted as "HH:mm:ss".
     *
     * @return A string representing the current time in "HH:mm:ss" format.
     */
    public String getFormattedCurrentTime() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }
}
