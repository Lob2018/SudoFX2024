package fr.softsf.sudokufx.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for internationalization (i18n) support. This class manages
 * language resources and provides methods to switch between languages and
 * retrieve localized strings.
 */
public final class I18n {

    private static final Locale LOCALE_FR = Locale.of("fr", "FR");
    private static final Locale LOCALE_EN = Locale.of("en", "US");
    private static final String I_18_N_PATH = MyEnums.Paths.I18N_PATH.getPath();
    private static final ResourceBundle FRENCH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_FR);
    private static final ResourceBundle ENGLISH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_EN);
    private static ResourceBundle bundle = FRENCH_BUNDLE;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private I18n() {
    }

    /**
     * Sets the current language for the application.
     *
     * @param i18n The String representation of the language ("EN" for English,
     * or any other value for French)
     */
    public static void setLocaleBundle(final String i18n) {
        bundle = switch (i18n) {
            case "EN" ->
                ENGLISH_BUNDLE;
            default ->
                FRENCH_BUNDLE;
        };
    }

    /**
     * Retrieves the localized string value for a given key.
     *
     * @param key The String key representing the desired localized string
     * @return The localized String value
     * @throws java.util.MissingResourceException if the key is not found in the
     * resource bundle
     */
    public static String getValue(final String key) {
        return bundle.getString(key);
    }

    /**
     * Gets the current language of the application.
     *
     * @return The language code as a String (e.g., "fr" for French, "en" for
     * English)
     */
    public static String getLanguage() {
        Locale locale = bundle.getLocale();
        return locale.getLanguage();
    }
}
