package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.utils.MyRegex;

/**
 * Utility class for managing JVM application properties. This class provides
 * methods to retrieve the application name and version from system properties.
 */
public final class JVMApplicationProperties {

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static String springContextExitOnRefresh = "spring.context.exit";
    private static String appName = "";
    private static String appVersion = "";
    private static String springContextExit;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JVMApplicationProperties() {
    }

    /**
     * Determines if the Spring context should exit on refresh.
     *
     * @return true if springContextExit is "onRefresh"; false otherwise.
     * If springContextExit is null, it retrieves the value from the system property
     * and re-evaluates the condition.
     */
    public static boolean isSpringContextExitOnRefresh() {
        return switch (springContextExit) {
            case null -> {
                springContextExit = System.getProperty(springContextExitOnRefresh);
                if (springContextExit == null) yield false;
                yield isSpringContextExitOnRefresh();
            }
            case "onRefresh" -> true;
            default -> false;
        };
    }

    /**
     * Sets the system property key to determine if the Spring context should exit on refresh.
     * This method is for testing purposes, replacing the default key ("spring.context.exit")
     * with a custom key (app.name) to ensure a non-null value during common.
     */
    static void setSpringContextExitInRefresh() {
        springContextExitOnRefresh = APP_NAME_PROPERTY;
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
     * Initialize the Spring context exit behavior to "_" for testing purposes.
     * This method modifies the static variable 'springContextExit' to simulate
     * the condition where the Spring context is configured to exit on refresh.
     */
    static void setInitSpringContextExitForTests() {
        springContextExit = null;
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
     * Sets the Spring context exit behavior to "never" for testing purposes.
     * This method modifies the static variable 'springContextExit' to simulate
     * the condition where the Spring context is configured to exit on refresh.
     */
    static void setNeverSpringContextExitForTests() {
        springContextExit = "never";
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
