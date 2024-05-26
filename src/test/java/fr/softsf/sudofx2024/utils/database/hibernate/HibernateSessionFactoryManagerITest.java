package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HibernateSessionFactoryManagerITest {

//    private static final OsDynamicFolders.IOsFoldersFactory iOsFolderFactory = new OsDynamicFolders(OS_NAME.getOs()).getIOsFoldersFactory();
//    private static final ApplicationKeystore keystore = new ApplicationKeystore(iOsFolderFactory);
//
//    @BeforeAll
//    static void setUpAll() {
//        // Flyway configuration
//        DatabaseMigration.configure(keystore, iOsFolderFactory);
//    }
//
//    @AfterEach
//    void tearDown() {
//        HibernateSessionFactoryManager.closeSessionFactory();
//    }
//
//    @Test
//    void testGetSessionFactory_Init_WithDefaultConfiguration_Success() {
//        // GIVEN
//        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
//        when(mockHibernateConfiguration.getHibernateSessionFactory()).thenReturn(mock(SessionFactory.class));
//        // WHEN
//        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactoryInit(mockHibernateConfiguration);
//        // THEN
//        assertEquals(sessionFactory, HibernateSessionFactoryManager.getSessionFactory());
//    }
//
//    @Test
//    void testGetSessionFactory_Init_WithCustomConfiguration_Success() {
//        // GIVEN
//        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
//        when(mockHibernateConfiguration.getHibernateSessionFactory()).thenReturn(mock(SessionFactory.class));
//        // WHEN
//        HibernateSessionFactoryManager.getSessionFactoryInit(mockHibernateConfiguration);
//        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();
//        // THEN
//        assertEquals(sessionFactory, HibernateSessionFactoryManager.getSessionFactory());
//    }
//
//    @Test
//    void testOpenAndCloseSession_Success() {
//        // GIVEN
//        Session session = HibernateSessionFactoryManager.getSessionFactoryInit(new HSQLDBSessionFactoryConfigurator(keystore, iOsFolderFactory)).openSession();
//        // WHEN THEN
//        assertTrue(session.isOpen(), "Expected the session to be open before closeSessionFactory()");
//        HibernateSessionFactoryManager.closeSessionFactory();
//        assertFalse(session.isOpen(), "Expected the session to be closed after calling closeSessionFactory()");
//        HibernateSessionFactoryManager.closeSessionFactory();
//        assertFalse(session.isOpen(), "Branch the session is already closed");
//    }
//
//    @Test
//    void testOpenAndCloseSessionAfterCloseFactoryCall_Success() {
//        // GIVEN
//        Session session = HibernateSessionFactoryManager.getSessionFactoryInit(new HSQLDBSessionFactoryConfigurator(keystore, iOsFolderFactory)).openSession();
//        // WHEN THEN
//        assertTrue(session.isOpen(), "Expected the session to be open before close()");
//        HibernateSessionFactoryManager.getSessionFactory().close();
//        HibernateSessionFactoryManager.closeSessionFactory();
//        assertFalse(session.isOpen(), "Expected the session to be closed after calling closeSessionFactory()");
//    }
//
//    @Test
//    void testGetSessionFactoryCatch_Exception() {
//        // GIVEN
//        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
//        doThrow(new RuntimeException(new Exception("Simulated exception"))).when(mockHibernateConfiguration).getHibernateSessionFactory();
//        // WHEN & THEN
//        Error exception = assertThrows(ExceptionInInitializerError.class, () -> {
//            HibernateSessionFactoryManager.getSessionFactory(mockHibernateConfiguration);
//        });
//        assertTrue(exception.getMessage().endsWith("Simulated exception"));
//    }
}
