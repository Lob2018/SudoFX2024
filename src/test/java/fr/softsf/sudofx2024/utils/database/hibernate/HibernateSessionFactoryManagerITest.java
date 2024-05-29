package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HibernateSessionFactoryManagerITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;
    @Autowired
    ApplicationKeystore keystore;

    @BeforeEach
    void beforeEach() {
        keystore.setupApplicationKeystore();
        DatabaseMigration.configure(keystore, osFolderFactory);
    }

    @AfterEach
    void tearDown() {
        HibernateSessionFactoryManager.closeSessionFactory();
    }

    @Test
    void testGetSessionFactory_Init_WithDefaultConfiguration_Success() {
        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
        when(mockHibernateConfiguration.getHibernateSessionFactory()).thenReturn(mock(SessionFactory.class));
        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory(mockHibernateConfiguration);
        assertEquals(sessionFactory, HibernateSessionFactoryManager.getSessionFactory());
    }

    @Test
    void testGetSessionFactory_Init_WithCustomConfiguration_Success() {
        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
        when(mockHibernateConfiguration.getHibernateSessionFactory()).thenReturn(mock(SessionFactory.class));
        HibernateSessionFactoryManager.getSessionFactory(mockHibernateConfiguration);
        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();
        assertEquals(sessionFactory, HibernateSessionFactoryManager.getSessionFactory());
    }

    @Test
    void testOpenAndCloseSession_Success() {
        Session session = HibernateSessionFactoryManager.getSessionFactory(new HSQLDBSessionFactoryConfigurator(keystore, osFolderFactory)).openSession();
        assertTrue(session.isOpen(), "Expected the session to be open before closeSessionFactory()");
        HibernateSessionFactoryManager.closeSessionFactory();
        assertFalse(session.isOpen(), "Expected the session to be closed after calling closeSessionFactory()");
        HibernateSessionFactoryManager.closeSessionFactory();
        assertFalse(session.isOpen(), "Branch the session is already closed");
    }

    @Test
    void testOpenAndCloseSessionAfterCloseFactoryCall_Success() {
        Session session = HibernateSessionFactoryManager.getSessionFactory(new HSQLDBSessionFactoryConfigurator(keystore, osFolderFactory)).openSession();
        assertTrue(session.isOpen(), "Expected the session to be open before close()");
        HibernateSessionFactoryManager.getSessionFactory().close();
        HibernateSessionFactoryManager.closeSessionFactory();
        assertFalse(session.isOpen(), "Expected the session to be closed after calling closeSessionFactory()");
    }

    @Test
    void testGetSessionFactoryCatch_Exception() {
        HibernateSessionFactoryManager.IHibernateConfiguration mockHibernateConfiguration = mock(HibernateSessionFactoryManager.IHibernateConfiguration.class);
        doThrow(new RuntimeException(new Exception("Simulated exception"))).when(mockHibernateConfiguration).getHibernateSessionFactory();
        Error exception = assertThrows(ExceptionInInitializerError.class, () -> {
            HibernateSessionFactoryManager.getSessionFactory(mockHibernateConfiguration);
        });
        assertTrue(exception.getMessage().endsWith("Simulated exception"));
    }
}
