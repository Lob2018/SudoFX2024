package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.utils.MyRegex;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s]+$";

    @Test
    void givenValidNameAndVersion_whenValidateByRegex_thenValidationSucceeds() {
        String validName = "MyApp";
        String validVersion = "0.0.1";
        assertTrue(MyRegex.INSTANCE.isValidatedByRegex(validName, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertTrue(MyRegex.INSTANCE.isValidatedByRegex(validVersion, Pattern.compile(VERSION_REGEX)));
    }

    @Test
    void givenInvalidNameAndVersion_whenValidateByRegex_thenValidationFails() {
        String invalidName = "MyApp123!";
        String invalidVersion = "0.0.";
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex(invalidName, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex(invalidVersion, Pattern.compile(VERSION_REGEX)));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("", Pattern.compile(VERSION_REGEX)));
    }

    @Test
    void givenNullSpringContext_whenGetAppNameAndVersion_thenDefaultValuesReturned() {
        JVMApplicationProperties.INSTANCE.setInitSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
    }

    @Test
    void givenEmptyAppNameAndVersionWithOnRefreshSpringContext_whenGetProperties_thenDefaultValuesAndExitOnRefresh() {
        JVMApplicationProperties.INSTANCE.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.INSTANCE.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.INSTANCE.setOnRefreshSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
        assertTrue(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }

    @Test
    void givenNeverSpringContextExit_whenCheckExitOnRefresh_thenExitNever() {
        JVMApplicationProperties.INSTANCE.setNeverSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }

    @Test
    void givenUnknownSpringContext_whenCheckExitOnRefresh_thenExitNotTriggered() {
        JVMApplicationProperties.INSTANCE.setSpringContextExitInRefresh();
        JVMApplicationProperties.INSTANCE.setInitSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }
}
