package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

public final class JVMApplicationProperties {

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static String appName = "";
    private static String appVersion = "";

    @ExcludedFromCoverageReportGenerated
    private JVMApplicationProperties() {
    }

    /**
     * Get the current application name
     *
     * @return The current application name
     */
    public static String getAppName() {
        if (appName.isEmpty()) {
            appName = MyRegex.isValidatedByRegex(System.getProperty(APP_NAME_PROPERTY), MyRegex.getALPHANUMERIC()) ? System.getProperty(APP_NAME_PROPERTY) : "";
        }
        return appName;
    }

    /**
     * Get the current application version
     *
     * @return The current application version
     */
    public static String getAppVersion() {
        if (appVersion.isEmpty()) {
            appVersion = MyRegex.isValidatedByRegex(System.getProperty(APP_VERSION_PROPERTY), MyRegex.getVERSION()) ? "v" + System.getProperty(APP_VERSION_PROPERTY) : "";
        }
        return appVersion;
    }

    /**
     * Stubbing setter for empty app name
     */
    static void setEmptyAppNamePropertyForTests() {
        appName = "";
    }

    /**
     * Stubbing setter for empty app version
     */
    static void setEmptyAppVersionPropertyForTests() {
        appVersion = "";
    }
}
