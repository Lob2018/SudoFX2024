package fr.softsf.sudofx2024.unit.utils.os;

import fr.softsf.sudofx2024.interfaces.IFileManager;
import fr.softsf.sudofx2024.interfaces.IOsFoldersFactory;
import fr.softsf.sudofx2024.utils.FileManagerImpl;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.OsInfo;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OsDynamicFoldersTest {

    private final IOsFoldersFactory REF_OBJ_WINDOWS_IOSFOLDERS = new OsDynamicFolders("windows").getIOsFoldersFactory();

    @Test
    public void testOsFolders_OS_exception() {
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
    public void testOsFolders_osToLowerCase_success() {
        String os = "Windows 10";
        System.setProperty("os.name", os);
        // GIVEN WHEN
        // THEN
        assertTrue(OsInfo.OS_NAME.contains("win"));
    }

    @Test
    public void testOsFolders_logsFolderPath_success() {
        // GIVEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory(new FileManagerImpl());
        // WHEN
        // THEN
        assertEquals(REF_OBJ_WINDOWS_IOSFOLDERS.getOsLogsFolderPath(), windowsFolderFactory.getOsLogsFolderPath());
    }

    @Test
    public void testOsFolders_dataFolderPath_success() {
        // GIVEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory(new FileManagerImpl());
        // WHEN
        // THEN
        assertEquals(REF_OBJ_WINDOWS_IOSFOLDERS.getOsDataFolderPath(), windowsFolderFactory.getOsDataFolderPath());
    }

    @Test
    public void testOsFolders_dataFolderPathWithNullFolderCreator_success() {
        // GIVEN
        IFileManager iFileManagerMock = null;
        // WHEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory(iFileManagerMock);
        String result = windowsFolderFactory.createDataFolder();
        // THEN
        assertEquals(windowsFolderFactory.getOsDataFolderPath(), result);
    }

    @Test
    public void testOsFolders_dataFolderPathAsBase_success() {
        // GIVEN
        IFileManager iFileManagerMock = mock(IFileManager.class);
        when(iFileManagerMock.isNowCreated(anyString())).thenReturn(false);
        // WHEN
        WindowsFolderFactory windowsFolderFactory = new WindowsFolderFactory(iFileManagerMock);
        String result = windowsFolderFactory.createDataFolder();
        // THEN
        assertEquals(result, windowsFolderFactory.getOsDataFolderPath());
    }
}
