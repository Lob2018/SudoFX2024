package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.utils.MyRegex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s]+$";

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
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
    }

    @Test
    void testEmptyAppNameEmptyAppVersionWithOnRefreshSpringContext_Success() {
        JVMApplicationProperties.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
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
