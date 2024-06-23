package fr.softsf.sudofx2024.utils.os;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class OsDynamicFoldersWindowsITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;

    @Test
    void testOsFolders_OS_exception() {
        OsDynamicFolders osDynamicFolders = new OsDynamicFolders();
        osDynamicFolders.setWrongOsForTests();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                osDynamicFolders::osFolderFactory
        );
        String expectedMessage = "Windows OS is needed to run SudoFX2024";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolders_OS_IS_NULL_exception() {
        OsDynamicFolders osDynamicFolders = new OsDynamicFolders();
        osDynamicFolders.setNullOsForTests();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                osDynamicFolders::osFolderFactory
        );
        String expectedMessage = "Windows OS is needed to run SudoFX2024";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolders_osToLowerCase_success() {
        String os = "Windows 10";
        System.setProperty("os.name", os);
        assertTrue(OS_NAME.getOs().contains("windows"));
    }

    @Test
    void testOsFolders_folderPath_success() {
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory();
        assertEquals(osFolderFactory.getOsLogsFolderPath(), windowsFolderFactory.getOsLogsFolderPath());
        assertEquals(osFolderFactory.getOsDataFolderPath(), windowsFolderFactory.getOsDataFolderPath());
    }

    @Test
    void testOsFolders_folderPathWithNullFolderCreator_success() {
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory();
        String[] results = windowsFolderFactory.createFolders();
        assertEquals(windowsFolderFactory.getOsDataFolderPath(), results[0]);
        assertEquals(windowsFolderFactory.getOsLogsFolderPath(), results[1]);
    }

    @Test
    void testOsFolders_folderPathAsBase_success() {
        WindowsFolderFactory windowsFolderFactory = Mockito.spy(new WindowsFolderFactory());
        when(windowsFolderFactory.isNowCreated(Mockito.anyString())).thenReturn(false);
        String[] results = windowsFolderFactory.createFolders();
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[0]);
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[1]);
    }

    @Test
    void testOsFolders_folderPathAsBase_oppositeBranches_success() {
        WindowsFolderFactory windowsFolderFactory = Mockito.spy(new WindowsFolderFactory());
        when(windowsFolderFactory.isNowCreated(SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath())).thenReturn(false);
        when(windowsFolderFactory.isNowCreated(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath())).thenReturn(true);
        String[] results = windowsFolderFactory.createFolders();
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[0]);
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[1]);
    }
}
