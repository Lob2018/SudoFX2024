package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.utils.MyRegex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SystemStubsExtension.class)
class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s]+$";
    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";

    @SystemStub
    private SystemProperties systemProperties;

    @BeforeEach
    void eachSetup() {
        systemProperties.set(APP_NAME_PROPERTY, "SudokuFX");
        systemProperties.set(APP_VERSION_PROPERTY, "1.0.0");
    }

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
    void testAppNameAppVersionWithNullSpringContext_Success() {
        JVMApplicationProperties.setInitSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
        assertEquals("v1.0.0", JVMApplicationProperties.getAppVersion());
        // appName.isEmpty()'s branch
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
        assertEquals("v1.0.0", JVMApplicationProperties.getAppVersion());
    }

    @Test
    void testEmptyAppNameEmptyAppVersionWithOnRefreshSpringContext_Success() {
        systemProperties.set(APP_NAME_PROPERTY, "");
        systemProperties.set(APP_VERSION_PROPERTY, "");
        JVMApplicationProperties.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        assertEquals("", JVMApplicationProperties.getAppName());
        assertEquals("", JVMApplicationProperties.getAppVersion());
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }

    @Test
    void testWithNeverSpringContext_Success() {
        JVMApplicationProperties.setNeverSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }

    @Test
    void testWithExistingSpringContext_Success() {
        JVMApplicationProperties.setSpringContextExitInRefresh();
        JVMApplicationProperties.setInitSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }
}
