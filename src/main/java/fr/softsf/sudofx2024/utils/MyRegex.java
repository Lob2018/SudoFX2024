package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for regular expression patterns and validation. This class
 * provides predefined regex patterns and a method for regex validation.
 */
public final class MyRegex {

    /**
     * Regex pattern for a secure password. Requirements: - At least 2 lowercase
     * characters - At least 2 uppercase characters - At least 2 numbers - At
     * least 2 special characters from @#$%^&()! - Exactly 24 characters in
     * total
     */
    @Getter
    private static final String SECRET = "^(?=(?:[^a-z]*[a-z]){2})(?=(?:[^A-Z]*[A-Z]){2})(?=(?:[^0-9]*[0-9]){2})(?=(?:[^@#$%^&()!]*[@#$%^&()!]){2})[a-zA-Z0-9@#$%^&()!]{24}$";

    /**
     * Regex pattern for semantic versioning format (X.Y.Z). Matches version
     * numbers like 1.0.0, 2.10.5, etc.
     */
    @Getter
    private static final String VERSION = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";

    /**
     * Regex pattern for alphanumeric strings (including spaces). Matches
     * strings containing only letters, numbers, and spaces.
     */
    @Getter
    private static final String ALPHANUMERIC = "^[a-zA-Z0-9\\s]+$";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    @ExcludedFromCoverageReportGenerated
    private MyRegex() {
    }

    /**
     * Validates a given text against a specified regular expression.
     *
     * @param text The text to validate (must not be null)
     * @param regex The regular expression to use for validation (must not be
     * null)
     * @return true if the text matches the regex pattern, false otherwise
     * @throws NullPointerException if either text or regex is null
     */
    public static boolean isValidatedByRegex(@NotNull final String text, @NotNull final String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
