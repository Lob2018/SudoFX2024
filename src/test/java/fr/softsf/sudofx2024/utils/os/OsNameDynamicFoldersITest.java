package fr.softsf.sudofx2024.utils.os;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OsNameDynamicFoldersITest {

    private final OsDynamicFolders.IOsFoldersFactory REF_OBJ_WINDOWS_IOSFOLDERS = new OsDynamicFolders("windows").getIOsFoldersFactory();

    @Test
    void testOsFolders_OS_exception() {
        // THEN
        IllegalArgumentException exception = assertThrows(
                //WHEN
                IllegalArgumentException.class,
                () -> {
                    // GIVEN
                    new OsDynamicFolders("linux");
                }
        );
        String expectedMessage = "Windows OS is needed to run SudoFX";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testOsFolders_osToLowerCase_success() {
        String os = "Windows 10";
        System.setProperty("os.name", os);
        // GIVEN WHEN
        // THEN
        assertTrue(OS_NAME.getOs().contains("win"));
    }

    @Test
    void testOsFolders_folderPath_success() {
        // GIVEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory();
        // WHEN
        // THEN
        assertEquals(REF_OBJ_WINDOWS_IOSFOLDERS.getOsLogsFolderPath(), windowsFolderFactory.getOsLogsFolderPath());
        assertEquals(REF_OBJ_WINDOWS_IOSFOLDERS.getOsDataFolderPath(), windowsFolderFactory.getOsDataFolderPath());
    }

    @Test
    void testOsFolders_folderPathWithNullFolderCreator_success() {
        // GIVEN
        // WHEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory();
        String[] results = windowsFolderFactory.createFolders();
        // THEN
        assertEquals(windowsFolderFactory.getOsDataFolderPath(), results[0]);
        assertEquals(windowsFolderFactory.getOsLogsFolderPath(), results[1]);
    }

    @Test
    void testOsFolders_folderPathAsBase_success() {
        // GIVEN
        WindowsFolderFactory windowsFolderFactory = Mockito.spy(new WindowsFolderFactory());
        when(windowsFolderFactory.isNowCreated(Mockito.anyString())).thenReturn(false);
        // WHEN
        String[] results = windowsFolderFactory.createFolders();
        // THEN
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[0]);
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[1]);
    }

    @Test
    void testOsFolders_folderPathAsBase_oppositeBranches_success() {
        // GIVEN
        WindowsFolderFactory windowsFolderFactory = Mockito.spy(new WindowsFolderFactory());
        when(windowsFolderFactory.isNowCreated(SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath())).thenReturn(false);
        when(windowsFolderFactory.isNowCreated(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath())).thenReturn(true);
        // WHEN
        String[] results = windowsFolderFactory.createFolders();
        // THEN
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[0]);
        assertEquals(SUDO_FX_BASE_PATH.getPath(), results[1]);
    }
}
