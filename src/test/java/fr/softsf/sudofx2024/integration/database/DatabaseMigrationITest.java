package fr.softsf.sudofx2024.integration.database;

import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;

import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.flywaydb.core.api.FlywayException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
class DatabaseMigrationITest {

    @Autowired
    static WindowsFolderFactory osFolderFactory;

    private static final IKeystore iKeystore = new ApplicationKeystore(osFolderFactory);

    @BeforeAll
    static void setUpAll() {
        // Flyway configuration
        DatabaseMigration.configure(iKeystore, osFolderFactory);
    }

    @AfterEach
    void tearDown() {
        HibernateSessionFactoryManager.closeSessionFactory();
    }

    @Test
    void testConfigureWithWrongPassword_fail() {
        IKeystore iKeystoreMock=mock(IKeystore.class);
        when(iKeystoreMock.getUsername()).thenReturn("");
        assertThrows(FlywayException.class, () -> DatabaseMigration.configure(iKeystoreMock, osFolderFactory));
    }
}
