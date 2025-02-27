package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.utils.MyRegex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s]+$";

    @Test
    void givenValidNameAndVersion_whenValidateByRegex_thenValidationSucceeds() {
        String validName = "MyApp";
        String validVersion = "0.0.1";
        assertTrue(MyRegex.isValidatedByRegex(validName, ALPHANUMERIC_REGEX));
        assertTrue(MyRegex.isValidatedByRegex(validVersion, VERSION_REGEX));
    }

    @Test
    void givenInvalidNameAndVersion_whenValidateByRegex_thenValidationFails() {
        String invalidName = "MyApp123!";
        String invalidVersion = "0.0.";
        assertFalse(MyRegex.isValidatedByRegex(invalidName, ALPHANUMERIC_REGEX));
        assertFalse(MyRegex.isValidatedByRegex(invalidVersion, VERSION_REGEX));
        assertFalse(MyRegex.isValidatedByRegex("", VERSION_REGEX));
    }

    @Test
    void givenNullSpringContext_whenGetAppNameAndVersion_thenDefaultValuesReturned() {
        JVMApplicationProperties.setInitSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
    }

    @Test
    void givenEmptyAppNameAndVersionWithOnRefreshSpringContext_whenGetProperties_thenDefaultValuesAndExitOnRefresh() {
        JVMApplicationProperties.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.getAppName());
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }

    @Test
    void givenNeverSpringContextExit_whenCheckExitOnRefresh_thenExitNever() {
        JVMApplicationProperties.setNeverSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }

    @Test
    void givenUnknownSpringContext_whenCheckExitOnRefresh_thenExitNotTriggered() {
        JVMApplicationProperties.setSpringContextExitInRefresh();
        JVMApplicationProperties.setInitSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
    }
}
