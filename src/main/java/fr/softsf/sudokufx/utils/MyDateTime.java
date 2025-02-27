package fr.softsf.sudokufx.utils;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for working with date and time.
 * This class provides static methods for date and time operations.
 */
public final class MyDateTime {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Clock clock = Clock.systemDefaultZone();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MyDateTime() {
    }

    /**
     * Returns the current time formatted as "HH:mm:ss".
     *
     * @return A string representing the current time in "HH:mm:ss" format.
     */
    public static String getFormattedCurrentTime() {
        return LocalDateTime.now(clock).format(TIME_FORMATTER);
    }
}
