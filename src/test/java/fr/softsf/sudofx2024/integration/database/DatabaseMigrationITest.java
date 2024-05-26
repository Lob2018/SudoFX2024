package fr.softsf.sudofx2024.integration.database;

import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;

import org.flywaydb.core.api.FlywayException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DatabaseMigrationITest {

    private static final OsDynamicFolders.IOsFoldersFactory iOsFolderFactory = new OsDynamicFolders(OS_NAME.getOs()).getIOsFoldersFactory();
    private static final IKeystore iKeystore = new ApplicationKeystore(iOsFolderFactory);

    @BeforeAll
    static void setUpAll() {
        // Flyway configuration
        DatabaseMigration.configure(iKeystore, iOsFolderFactory);
    }

    @AfterEach
    void tearDown() {
        HibernateSessionFactoryManager.closeSessionFactory();
    }

    @Test
    void testConfigureWithWrongPassword_fail() {
        IKeystore iKeystoreMock=mock(IKeystore.class);
        when(iKeystoreMock.getUsername()).thenReturn("");
        assertThrows(FlywayException.class, () -> DatabaseMigration.configure(iKeystoreMock, iOsFolderFactory));
    }
}

