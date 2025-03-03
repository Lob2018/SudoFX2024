package fr.softsf.sudokufx.common.unit.utils;

import fr.softsf.sudokufx.exception.ExceptionTools;
import org.junit.jupiter.api.Test;

import java.sql.SQLInvalidAuthorizationSpecException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {

    private final ExceptionTools exceptionTools = ExceptionTools.INSTANCE;

    @Test
    void givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsSQLInvalidAuthorizationSpecException() {
        Throwable t = new Throwable(new SQLInvalidAuthorizationSpecException());
        SQLInvalidAuthorizationSpecException result = exceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
    }

    @Test
    void givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsNull() {
        Throwable t = new Throwable(new Exception());
        SQLInvalidAuthorizationSpecException result = exceptionTools.getSQLInvalidAuthorizationSpecException(t);
        assertNull(result);
    }
}
