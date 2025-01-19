package fr.softsf.sudokufx.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s]+$";
    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static final String SPRING_CONTEXT_EXIT_ON_REFRESH = "spring.context.exit";

    @Test
    void testIsValidatedByRegex_Success() {
        String validName = "MyApp";
        String validVersion = "0.0.1";
        assertTrue(MyRegex.isValidatedByRegex(validName, ALPHANUMERIC_REGEX));
        assertTrue(MyRegex.isValidatedByRegex(validVersion, VERSION_REGEX));
    }

    @Test
    void testIsValidatedByRegex_Fail() {
        String invalidName = "MyApp123!";
        String invalidVersion = "0.0.";
        assertFalse(MyRegex.isValidatedByRegex(invalidName, ALPHANUMERIC_REGEX));
        assertFalse(MyRegex.isValidatedByRegex(invalidVersion, VERSION_REGEX));
        assertFalse(MyRegex.isValidatedByRegex("", VERSION_REGEX));
    }

    @Test
    void testGetAppProperties_Success() {
        System.setProperty(APP_NAME_PROPERTY, "_");
        System.setProperty(APP_VERSION_PROPERTY, "_");
        JVMApplicationProperties.setEmptySpringContextExitForTests();
        assertEquals("", JVMApplicationProperties.getAppName());
        assertEquals("", JVMApplicationProperties.getAppVersion());
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
        JVMApplicationProperties.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        System.setProperty(APP_NAME_PROPERTY, "MyApp");
        System.setProperty(APP_VERSION_PROPERTY, "1.0.0");
        assertEquals("MyApp", JVMApplicationProperties.getAppName());
        assertEquals("v1.0.0", JVMApplicationProperties.getAppVersion());
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assertEquals("MyApp", JVMApplicationProperties.getAppName());
        assertEquals("v1.0.0", JVMApplicationProperties.getAppVersion());
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
        JVMApplicationProperties.setNeverSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }
}
