package fr.softsf.sudofx2024.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static final I18n INSTANCE = new I18n();
    private final Locale LOCALE_FR = Locale.of("fr", "FR");
    private final Locale LOCALE_EN = Locale.of("en", "US");
    private final String I18N_PATH = "/fr/softsf/sudofx2024/i18n/resource";
    private final ResourceBundle FRENCH_BUNDLE = ResourceBundle.getBundle(I18N_PATH, LOCALE_FR);
    private final ResourceBundle ENGLISH_BUNDLE = ResourceBundle.getBundle(I18N_PATH, LOCALE_EN);
    private ResourceBundle BUNDLE= FRENCH_BUNDLE;

    /**
     * Private constructor cannot be instantiated outside I18n class
     * I18n initialization
     */
    private I18n() {
    }

    /**
     * Get the unique instance of the I18n class
     *
     * @return The unique I18n class instance
     */
    public static I18n getInstance() {
        return INSTANCE;
    }

    /**
     * Set the current language
     *
     * @param i18n The String representation of the language ("EN" for english or French by default)
     */
    public final void setLocaleBundle(String i18n) {
        BUNDLE = switch (i18n) {
            case "EN" -> ENGLISH_BUNDLE;
            default -> FRENCH_BUNDLE;
        };
    }

    /**
     * Get the I18N current String value by String key
     *
     * @param key The String key I18N
     * @return The String value
     */
    public final String getValue(String key) {
        return BUNDLE.getString(key);
    }


    /**
     * Get current language
     *
     * @return The language String value
     */
    public final String getLanguage() {
        Locale locale = BUNDLE.getLocale();
        return locale.getLanguage();
    }


}
