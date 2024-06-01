package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.model.*;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HSQLDBSessionFactoryConfiguratorITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;
    @Autowired
    ApplicationKeystore keystore;

    private HSQLDBSessionFactoryConfigurator configurator;

    @BeforeEach
    void beforeEach() {
        keystore.setupApplicationKeystore();
        DatabaseMigration.configure(keystore, osFolderFactory);
        configurator= new HSQLDBSessionFactoryConfigurator(keystore, osFolderFactory);
    }

    @AfterEach
    void tearDown() {
        HibernateSessionFactoryManager.closeSessionFactory();
    }

    @Test
    void testGetSessionFactory_Success() {
        SessionFactory sessionFactory = configurator.getHibernateSessionFactory();
        Assertions.assertNotNull(sessionFactory);
        Metamodel metamodel = sessionFactory.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            if (entityType.getJavaType().getName().equals(Software.class.getName())
                    || entityType.getJavaType().getName().equals(PlayerLanguage.class.getName())
                    || entityType.getJavaType().getName().equals(Background.class.getName())
                    || entityType.getJavaType().getName().equals(GameLevel.class.getName())
                    || entityType.getJavaType().getName().equals(Grid.class.getName())
                    || entityType.getJavaType().getName().equals(Player.class.getName())
                    || entityType.getJavaType().getName().equals(Menu.class.getName())
                    || entityType.getJavaType().getName().equals(Game.class.getName())) {
                Assertions.assertTrue(true);
            } else {
                Assertions.fail("Entité non trouvée : " + entityType.getJavaType().getName());
            }
        }
        SessionFactory sessionFactoryAlreadyCreated = configurator.getHibernateSessionFactory();
        Assertions.assertNotNull(sessionFactoryAlreadyCreated);
        Assertions.assertSame(sessionFactory, sessionFactoryAlreadyCreated);
    }

    @Test
    void testGetSessionFactory_throws_Exception() {
        HSQLDBSessionFactoryConfigurator configuratorSpy = Mockito.spy(configurator);
        when(configuratorSpy.getDataFolderPathForTests()).thenThrow(new NullPointerException("Simulated exception"));
        NullPointerException exception = assertThrows(NullPointerException.class, configuratorSpy::getHibernateSessionFactory);
        assertEquals("Simulated exception", exception.getMessage());
    }
}
