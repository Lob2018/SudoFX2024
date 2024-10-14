package fr.softsf.sudofx2024.integration.database;

import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;

import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.flywaydb.core.api.FlywayException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
class DatabaseMigrationWIndowsITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;

    @Autowired
    ApplicationKeystore keystore;

    @BeforeEach
    void beforeEach() {
        keystore.setupApplicationKeystore();
        DatabaseMigration.configure(keystore, osFolderFactory);
    }

    @Test
    void testConfigureWithWrongPassword_fail() {
        IKeystore iKeystoreMock = mock(IKeystore.class);
        when(iKeystoreMock.getUsername()).thenReturn("");
        assertThrows(FlywayException.class, () -> DatabaseMigration.configure(iKeystoreMock, osFolderFactory));
    }
}

