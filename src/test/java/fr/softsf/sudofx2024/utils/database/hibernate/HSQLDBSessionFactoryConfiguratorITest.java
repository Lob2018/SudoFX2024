package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.model.*;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.USER_HOME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HSQLDBSessionFactoryConfiguratorITest {

    @Autowired
    static WindowsFolderFactory osFolderFactory;

    private static HSQLDBSessionFactoryConfigurator configurator;
    private static final ApplicationKeystore iKeystore = new ApplicationKeystore(osFolderFactory);

    @BeforeAll
    static void setUpAll() {
        DatabaseMigration.configure(iKeystore, osFolderFactory);
        configurator = new HSQLDBSessionFactoryConfigurator(iKeystore, osFolderFactory);
    }

    @AfterEach
    void tearDown() {
        HibernateSessionFactoryManager.closeSessionFactory();
    }

    private static OsDynamicFolders.IOsFoldersFactory init() {
        OsDynamicFolders.IOsFoldersFactory mockIOsFolderFactory = Mockito.mock(OsDynamicFolders.IOsFoldersFactory.class);
        Mockito.when(mockIOsFolderFactory.getOsDataFolderPath()).thenReturn(USER_HOME.getPath().replace("\\", "/") + "/AppData/Local/Soft64.fr/SudokuFX/data-sudofx");
        return mockIOsFolderFactory;
    }

    @Test
    void testGetSessionFactory_Success() {
        SessionFactory sessionFactory = configurator.getHibernateSessionFactory();
        Assertions.assertNotNull(sessionFactory);
        // All entity classes are available
        Metamodel metamodel = sessionFactory.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            if (entityType.getJavaType().getName().equals(SoftwareModel.class.getName())
                    || entityType.getJavaType().getName().equals(PlayerLanguageModel.class.getName())
                    || entityType.getJavaType().getName().equals(BackgroundModel.class.getName())
                    || entityType.getJavaType().getName().equals(GameLevelModel.class.getName())
                    || entityType.getJavaType().getName().equals(GridModel.class.getName())
                    || entityType.getJavaType().getName().equals(PlayerModel.class.getName())
                    || entityType.getJavaType().getName().equals(MenuModel.class.getName())
                    || entityType.getJavaType().getName().equals(GameModel.class.getName())) {
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
        // Stubbing getDataFolderPath() to throw an Exception
        HSQLDBSessionFactoryConfigurator configuratorSpy = Mockito.spy(configurator);
        when(configuratorSpy.getDataFolderPath()).thenThrow(new NullPointerException("Simulated exception"));
        // WHEN & THEN
        NullPointerException exception = assertThrows(NullPointerException.class, configuratorSpy::getHibernateSessionFactory);
        assertEquals("Simulated exception", exception.getMessage());
    }
}
