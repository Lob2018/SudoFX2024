package fr.softsf.sudokufx.enums;

import fr.softsf.sudokufx.utils.MyEnums;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility enum for internationalization (i18n) support. This class manages
 * language resources and provides methods to switch between languages and
 * retrieve localized strings.
 */
public enum I18n {

    INSTANCE;

    private static final Locale LOCALE_FR = Locale.of("fr", "FR");
    private static final Locale LOCALE_EN = Locale.of("en", "US");
    private static final String I_18_N_PATH = MyEnums.Paths.I18N_PATH.getPath();
    private static final ResourceBundle FRENCH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_FR);
    private static ResourceBundle bundle = FRENCH_BUNDLE;
    private static final ResourceBundle ENGLISH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_EN);

    /**
     * Sets the application's current language.
     *
     * @param i18n The language code ("EN" for English, any other value for French).
     * @return The singleton instance of I18n.
     */
    public static I18n setLocaleBundle(final String i18n) {
        I18n.bundle = "EN".equals(i18n) ? ENGLISH_BUNDLE : FRENCH_BUNDLE;
        return INSTANCE;
    }

    /**
     * Retrieves the localized string value for a given key.
     *
     * @param key The String key representing the desired localized string
     * @return The localized String value
     * @throws java.util.MissingResourceException if the key is not found in the
     *                                            resource bundle
     */
    public String getValue(final String key) {
        return bundle.getString(key);
    }

    /**
     * Gets the current language of the application.
     *
     * @return The language code as a String (e.g., "fr" for French, "en" for
     * English)
     */
    public String getLanguage() {
        Locale locale = bundle.getLocale();
        return locale.getLanguage();
    }

    /**
     * Gets the language code of the host environment.
     * Uses the default locale to return the language code (e.g., "en" for English, "fr" for French).
     *
     * @return The language code as a String.
     */
    public String getHostEnvironmentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Sets the locale bundle based on the host environment's language.
     * If the host environment's language is French ("fr"), uses the default locale bundle.
     * Otherwise, uses the English locale bundle ("EN").
     */
    public void setLanguageBasedOnTheHostEnvironment() {
        setLocaleBundle("fr".equals(getHostEnvironmentLanguageCode()) ? "" : "EN");
    }

}
