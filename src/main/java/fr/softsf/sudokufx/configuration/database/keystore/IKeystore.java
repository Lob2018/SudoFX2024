package fr.softsf.sudokufx.configuration.database.keystore;

/**
 * Interface defining methods to get the database username and his password.
 */
public sealed interface IKeystore permits ApplicationKeystore {

    /**
     * Set up the application keystore
     */
    void setupApplicationKeystore();

    /**
     * Get database username
     *
     * @return The database username
     */
    String getUsername();

    /**
     * Get database password
     *
     * @return The database password
     */
    String getPassword();
}