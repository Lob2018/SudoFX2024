package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

/**
 * Utility class for managing JVM application properties. This class provides
 * methods to retrieve the application name and version from system properties.
 */
public final class JVMApplicationProperties {

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static String appName = "";
    private static String appVersion = "";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    @ExcludedFromCoverageReportGenerated
    private JVMApplicationProperties() {
    }

    /**
     * Retrieves the current application name from system properties. The
     * application name is validated against an alphanumeric regex pattern.
     *
     * @return The current application name if valid, or an empty string if
     * invalid or not set.
     */
    public static String getAppName() {
        if (appName.isEmpty()) {
            appName = MyRegex.isValidatedByRegex(System.getProperty(APP_NAME_PROPERTY), MyRegex.getALPHANUMERIC()) ? System.getProperty(APP_NAME_PROPERTY) : "";
        }
        return appName;
    }

    /**
     * Retrieves the current application version from system properties. The
     * version is validated against a version regex pattern and prefixed with
     * 'v'.
     *
     * @return The current application version prefixed with 'v' if valid, or an
     * empty string if invalid or not set.
     */
    public static String getAppVersion() {
        if (appVersion.isEmpty()) {
            appVersion = MyRegex.isValidatedByRegex(System.getProperty(APP_VERSION_PROPERTY), MyRegex.getVERSION()) ? "v" + System.getProperty(APP_VERSION_PROPERTY) : "";
        }
        return appVersion;
    }

    /**
     * Resets the application name to an empty string. This method is intended
     * for testing purposes only.
     */
    static void setEmptyAppNamePropertyForTests() {
        appName = "";
    }

    /**
     * Resets the application version to an empty string. This method is
     * intended for testing purposes only.
     */
    static void setEmptyAppVersionPropertyForTests() {
        appVersion = "";
    }
}
