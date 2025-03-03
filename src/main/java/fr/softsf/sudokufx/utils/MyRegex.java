package fr.softsf.sudokufx.utils;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility enum for regular expression patterns and validation.
 * Provides predefined regex patterns and a method for regex validation.
 */
@Slf4j
public enum MyRegex {

    INSTANCE;

    /**
     * Precompiled regex pattern for a secure password.
     * Requirements:
     * - At least 2 lowercase characters
     * - At least 2 uppercase characters
     * - At least 2 numbers
     * - At least 2 special characters from @#$%^&()!
     * - Exactly 24 characters in total
     */
    @Getter
    private final Pattern secretPattern = Pattern.compile(
            "^(?=(?:[^a-z]*[a-z]){2})(?=(?:[^A-Z]*[A-Z]){2})(?=(?:\\D*\\d){2})(?=(?:[^@#$%^&()!]*[@#$%^&()!]){2})[a-zA-Z0-9@#$%^&()!]{24}$"
    );

    /**
     * Precompiled regex pattern for semantic versioning (X.Y.Z).
     */
    @Getter
    private final Pattern versionPattern = Pattern.compile(
            "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$"
    );

    /**
     * Precompiled regex pattern for alphanumeric strings (including spaces).
     */
    @Getter
    private final Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9\\s]+$");

    /**
     * Validates the given text against the specified regular expression.
     *
     * @param text    The text to validate (must not be null).
     * @param pattern The regular expression pattern for validation (must not be null).
     * @return true if the text matches the regex pattern; false otherwise.
     */
    public boolean isValidatedByRegex(@NotNull final String text, @NotNull final Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        } catch (Exception ex) {
            log.error("██ Exception caught inside isValidatedByRegex, text is {}, pattern is {}, regex is {} : {}", text, pattern, (pattern == null) ? null : pattern.pattern(), ex.getMessage(), ex);
            return false;
        }
    }
}
