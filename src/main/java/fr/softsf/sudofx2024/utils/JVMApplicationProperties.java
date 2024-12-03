package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

/**
 * Utility class for managing JVM application properties. This class provides
 * methods to retrieve the application name and version from system properties.
 */
public final class JVMApplicationProperties {

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static final String SPRING_CONTEXT_EXIT_ON_REFRESH = "spring.context.exit";
    private static String appName = "";
    private static String appVersion = "";
    private static String springContextExit = "";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    @ExcludedFromCoverageReportGenerated
    private JVMApplicationProperties() {
    }

    /**
     * Checks if the Spring context should exit on refresh.
     * If the 'springContextExit' variable is empty, it retrieves the value
     * from the system property specified by SPRING_CONTEXT_EXIT_ON_REFRESH.
     * The method returns true if the value is "onRefresh"; otherwise, it returns false.
     *
     * @return true if the context should exit on refresh; false otherwise.
     */
    public static boolean isSpringContextExitOnRefresh() {
        if (springContextExit.isEmpty()) {
            springContextExit = System.getProperty(SPRING_CONTEXT_EXIT_ON_REFRESH);
        }
        return "onRefresh".equals(springContextExit);
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
     * Sets the Spring context exit behavior to "onRefresh" for testing purposes.
     * This method modifies the static variable 'springContextExit' to simulate
     * the condition where the Spring context is configured to exit on refresh.
     */
    static void setOnRefreshSpringContextExitForTests() {
        springContextExit = "onRefresh";
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
