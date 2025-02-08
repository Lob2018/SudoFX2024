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
    private static ResourceBundle bundle = FRENCH_BUNDLE;
    private static final ResourceBundle ENGLISH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_EN);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private I18n() {
    }

    /**
     * Sets the current language for the application.
     *
     * @param i18n The String representation of the language ("EN" for English,
     *             or any other value for French)
     */
    public static void setLocaleBundle(final String i18n) {
        bundle = "EN".equals(i18n) ? ENGLISH_BUNDLE : FRENCH_BUNDLE;
    }

    /**
     * Retrieves the localized string value for a given key.
     *
     * @param key The String key representing the desired localized string
     * @return The localized String value
     * @throws java.util.MissingResourceException if the key is not found in the
     *                                            resource bundle
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

    /**
     * Gets the language code of the host environment.
     * Uses the default locale to return the language code (e.g., "en" for English, "fr" for French).
     *
     * @return The language code as a String.
     */
    public static String getHostEnvironmentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Sets the locale bundle based on the host environment's language.
     * If the host environment's language is French ("fr"), uses the default locale bundle.
     * Otherwise, uses the English locale bundle ("EN").
     */
    public static void setLanguageBasedOnTheHostEnvironment() {
        setLocaleBundle("fr".equals(getHostEnvironmentLanguageCode()) ? "" : "EN");
    }

}
