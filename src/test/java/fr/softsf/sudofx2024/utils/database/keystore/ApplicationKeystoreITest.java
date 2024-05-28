package fr.softsf.sudofx2024.utils.database.keystore;

import fr.softsf.sudofx2024.SudoMain;
import fr.softsf.sudofx2024.utils.FileSystemManager;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;

import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.junit.jupiter.api.*;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.SUPPOSED_DATA_FOLDER_FOR_SUDO_FX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.slf4j.LoggerFactory;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

@SpringBootTest
class ApplicationKeystoreITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;

    private static OsDynamicFolders.IOsFoldersFactory iOsFolderFactoryMocked;

    private ListAppender<ILoggingEvent> logWatcher;

    private static String passInit;
    private static String userInit;

    @BeforeAll
    static void setupAll() {
        new FileSystemManager().deleteFolder(Paths.get(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath()), SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath());
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
        ApplicationKeystore applicationKeystoreInit = new ApplicationKeystore(osFolderFactory);
        passInit = applicationKeystoreInit.getPassword();
        userInit = applicationKeystoreInit.getUsername();
        assertEquals(passInit.length(), userInit.length(), 24);
    }

    @Test
    @Order(1)
    void constructorForAlreadyExistingKeystore_success() {
        ApplicationKeystore applicationKeystoreInit = new ApplicationKeystore(osFolderFactory);
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
