package fr.softsf.sudofx2024.utils.database.keystore;

import fr.softsf.sudofx2024.utils.FileSystemManager;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;

import org.junit.jupiter.api.*;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.SUPPOSED_DATA_FOLDER_FOR_SUDO_FX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.slf4j.LoggerFactory;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Logger;

import java.nio.file.Paths;


class ApplicationKeystoreITest {

    private static OsDynamicFolders.IOsFoldersFactory iOsFolderFactoryMocked;
    private static OsDynamicFolders.IOsFoldersFactory iOsFolderFactory;

    private ListAppender<ILoggingEvent> logWatcher;

    private static String passInit;
    private static String userInit;

    @BeforeAll
    static void setupAll() {
        OsDynamicFolders.IOsFoldersFactory iOsFolderFactoryInit = new OsDynamicFolders(OS_NAME.getOs()).getIOsFoldersFactory();
        new FileSystemManager().deleteFolder(Paths.get(iOsFolderFactoryInit.getOsDataFolderPath()), SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath());
        iOsFolderFactory = new OsDynamicFolders(OS_NAME.getOs()).getIOsFoldersFactory();
    }

    @BeforeEach
    void setup() {
        iOsFolderFactoryMocked = mock(OsDynamicFolders.IOsFoldersFactory.class);
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(ApplicationKeystore.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        iOsFolderFactoryMocked = null;
        ((Logger) LoggerFactory.getLogger(ApplicationKeystore.class)).detachAndStopAllAppenders();
    }

    @Test
    @Order(0)
    void constructorForKeystoreInitialization_success() {
        ApplicationKeystore applicationKeystoreInit = new ApplicationKeystore(iOsFolderFactory);
        passInit = applicationKeystoreInit.getPassword();
        userInit = applicationKeystoreInit.getUsername();
        assertEquals(passInit.length(), userInit.length(), 24);
    }

    @Test
    @Order(1)
    void constructorForAlreadyExistingKeystore_success() {
        ApplicationKeystore applicationKeystoreInit = new ApplicationKeystore(iOsFolderFactory);
        String pass = applicationKeystoreInit.getPassword();
        String user = applicationKeystoreInit.getUsername();
        assertEquals(pass.length(), user.length(), 24);
        assertEquals(user, userInit);
        assertEquals(pass, passInit);
    }

    @Test
    void constructorException_fail() {
        // GIVEN
        when(iOsFolderFactoryMocked.getOsDataFolderPath()).thenThrow(new RuntimeException(new Exception("██ Exception")));
        // WHEN
        new ApplicationKeystore(iOsFolderFactoryMocked);
        // THEN
        verify(iOsFolderFactoryMocked).getOsDataFolderPath();
        assert (logWatcher.list.get(1).getFormattedMessage()).contains("██ Exception");
    }
}
