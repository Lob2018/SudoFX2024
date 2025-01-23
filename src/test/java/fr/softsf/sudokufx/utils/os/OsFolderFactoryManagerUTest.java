package fr.softsf.sudokufx.utils.os;

import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OsFolderFactoryManagerUTest {
    private OsFolderFactoryManager osFolderFactoryManager;
    private IOsFolderFactory currentIOsFolderFactory;

    static Stream<String> provideInvalidOperatingSystems() {
        return Stream.of(null, "");
    }

    @BeforeEach
    void setup() {
        osFolderFactoryManager = new OsFolderFactoryManager();
        currentIOsFolderFactory = osFolderFactoryManager.osFolderFactory();
    }

    @Test
    void testOsFoldersWithInvalidOs() {
        assertThrows(IllegalArgumentException.class, () -> {
            osFolderFactoryManager.setWrongOsForTests();
            osFolderFactoryManager.osFolderFactory();
        }, "Unsupported OS: ");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidOperatingSystems")
    void testOsFoldersWithNullOrEmptyOs(String os) {
        if (os == null) {
            osFolderFactoryManager.setNullOsForTests();
        } else if (os.isEmpty()) {
            osFolderFactoryManager.setEmptyOsForTests();
        }

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, osFolderFactoryManager::osFolderFactory);
        assertTrue(exception.getMessage().contains("Operating system is not specified or null."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Windows", "Linux", "MacOS"})
    void testOsFolderFactoryWithValidOS(String osType) {
        switch (osType) {
            case "Windows":
                osFolderFactoryManager.setWindowsOsForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
                break;
            case "Linux":
                osFolderFactoryManager.setLinuxOsForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
                break;
            case "MacOS":
                osFolderFactoryManager.setMacOSForTests(currentIOsFolderFactory.getOsDataFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
                break;
            default:
                throw new IllegalArgumentException("Unknown OS type");
        }
        IOsFolderFactory factory = osFolderFactoryManager.osFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), currentIOsFolderFactory.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
    }
}


