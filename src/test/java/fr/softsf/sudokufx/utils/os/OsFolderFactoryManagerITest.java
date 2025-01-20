package fr.softsf.sudokufx.utils.os;

import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OsFolderFactoryManagerITest {

    private static IOsFolderFactory currentIOsFolderFactory;

    @BeforeAll
    public static void setUp() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        currentIOsFolderFactory = osFolderFactoryManager.osFolderFactory();
    }

    @Test
    void testOsFolders_OS_exception() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setWrongOsForTests();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                osFolderFactoryManager::osFolderFactory
        );
        String expectedMessage = "Unsupported OS: ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolders_OS_IS_NULL_exception() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setNullOsForTests();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                osFolderFactoryManager::osFolderFactory
        );
        String expectedMessage = "Operating system is not specified or null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolders_OS_IS_EMPTY_exception() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setEmptyOsForTests();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                osFolderFactoryManager::osFolderFactory
        );
        String expectedMessage = "Operating system is not specified or null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolderFactoryWithWindowsFolderFactory() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setWindowsOsForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
        IOsFolderFactory factory = osFolderFactoryManager.osFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), currentIOsFolderFactory.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
    }

    @Test
    void testOsFolderFactoryWithLinuxFolderFactory() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setLinuxOsForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
        IOsFolderFactory factory = osFolderFactoryManager.osFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), currentIOsFolderFactory.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
    }

    @Test
    void testOsFolderFactoryWithMacOSFolderFactory() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        osFolderFactoryManager.setMacOSForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
        IOsFolderFactory factory = osFolderFactoryManager.osFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), currentIOsFolderFactory.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
    }
}
