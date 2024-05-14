package fr.softsf.sudofx2024.utils;


import java.util.Locale;
import java.util.ResourceBundle;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.I18N_PATH;

public class I18n {
    private static final Locale LOCALE_FR = Locale.of("fr", "FR");
    private static final Locale LOCALE_EN = Locale.of("en", "US");
    private static final String I18N_PATH_ = I18N_PATH.getPath();
    private static final ResourceBundle FRENCH_BUNDLE = ResourceBundle.getBundle(I18N_PATH_, LOCALE_FR);
    private static final ResourceBundle ENGLISH_BUNDLE = ResourceBundle.getBundle(I18N_PATH_, LOCALE_EN);
    private static ResourceBundle bundle = FRENCH_BUNDLE;

    private I18n() {}

    /**
     * Set the current language
     *
     * @param i18n The String representation of the language ("EN" for english or French by default)
     */
    public static final void setLocaleBundle(String i18n) {
        bundle = switch (i18n) {
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
    public static final String getValue(String key) {
        return bundle.getString(key);
    }

    /**
     * Get current language
     *
     * @return The language String value
     */
    public static String getLanguage() {
        Locale locale = bundle.getLocale();
        return locale.getLanguage();
    }
}
