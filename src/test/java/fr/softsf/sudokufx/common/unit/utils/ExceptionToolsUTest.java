package fr.softsf.sudokufx.common.unit.utils;

import fr.softsf.sudokufx.exception.ExceptionTools;
import org.junit.jupiter.api.Test;

import java.sql.SQLInvalidAuthorizationSpecException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {
    @Test
    void givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsSQLInvalidAuthorizationSpecException() {
        Throwable t = new Throwable(new SQLInvalidAuthorizationSpecException());
        SQLInvalidAuthorizationSpecException result = ExceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
    }

    @Test
    void givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsNull() {
        Throwable t = new Throwable(new Exception());
        SQLInvalidAuthorizationSpecException result = ExceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertNull(result);
    }
}
