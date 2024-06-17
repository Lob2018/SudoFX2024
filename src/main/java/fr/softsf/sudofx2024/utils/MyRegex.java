package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MyRegex {

    /**
     * At least 2 lowercase characters
     * at least 2 uppercase characters
     * at least 2 numbers
     * at least 2 of these specials characters
     * 12 characters
     */
    @Getter
    private static final String SECRET = "^(?=(?:[^a-z]*[a-z]){2})(?=(?:[^A-Z]*[A-Z]){2})(?=(?:[^0-9]*[0-9]){2})(?=(?:[^@#$%^&()!]*[@#$%^&()!]){2})[a-zA-Z0-9@#$%^&()!]{24}$";

    /**
     * Semantic versioning format (X.Y.Z)
     */
    @Getter
    private static final String VERSION = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    @Getter
    private static final String ALPHANUMERIC = "^[a-zA-Z0-9\\s]+$";

    @ExcludedFromCoverageReportGenerated
    private MyRegex() {
    }

    /**
     * Validate text with regular expression
     *
     * @param text  The text to validate
     * @param regex The regular expression
     * @return True if the text is validated by regex, False otherwise
     */
    public static boolean isValidatedByRegex(@NotNull final String text, @NotNull final String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
