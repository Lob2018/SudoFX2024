package fr.softsf.sudofx2024.utils;

import java.sql.SQLInvalidAuthorizationSpecException;

public final class ExceptionTools {
    private ExceptionTools() {
    }

    /**
     * Find SQLInvalidAuthorizationSpecException recursively in a Throwable
     * @param e The Throwable
     * @return The SQLInvalidAuthorizationSpecException found otherwise null
     */
    public static SQLInvalidAuthorizationSpecException getSQLInvalidAuthorizationSpecException(Throwable e) {
        while (e != null) {
            if (e instanceof SQLInvalidAuthorizationSpecException sqlinvalidauthorizationspecexception) {
                return sqlinvalidauthorizationspecexception;
            }
            e = e.getCause();
        }
        return null;
    }

}
