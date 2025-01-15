package fr.softsf.sudokufx.integration.utils;

import fr.softsf.sudokufx.utils.ExceptionTools;
import org.junit.jupiter.api.Test;

import java.sql.SQLInvalidAuthorizationSpecException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {
    @Test
    void getSQLInvalidAuthorizationSpecException_success() {
        Throwable t = new Throwable(new SQLInvalidAuthorizationSpecException());
        SQLInvalidAuthorizationSpecException result = ExceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
    }

    @Test
    void getSQLInvalidAuthorizationSpecException_null() {
        Throwable t = new Throwable(new Exception());
        SQLInvalidAuthorizationSpecException result = ExceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertNull(result);
    }
}
