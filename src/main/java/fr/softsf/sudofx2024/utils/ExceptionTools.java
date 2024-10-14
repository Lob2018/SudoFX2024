package fr.softsf.sudofx2024.utils;

import java.sql.SQLInvalidAuthorizationSpecException;

/**
 * Utility class for handling and analyzing exceptions. This class provides
 * methods to search for specific exception types within exception chains.
 */
public final class ExceptionTools {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ExceptionTools() {
    }

    /**
     * Recursively searches for a SQLInvalidAuthorizationSpecException in the
     * exception chain. This method traverses the exception hierarchy starting
     * from the given Throwable, looking for an instance of
     * SQLInvalidAuthorizationSpecException.
     *
     * @param e The Throwable to start the search from. This can be any
     * exception or error.
     * @return The first SQLInvalidAuthorizationSpecException found in the
     * exception chain, or null if no such exception is found.
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
