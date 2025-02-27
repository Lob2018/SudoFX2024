package fr.softsf.sudokufx.configuration.database;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.configuration.os.IMockIOsFolderFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SudoMain.class})
class ApplicationKeystoreITest {

    private static String passInit;
    private static String userInit;
    private final ApplicationKeystore keystore;
    private ListAppender<ILoggingEvent> logWatcher;

    @Autowired
    public ApplicationKeystoreITest(ApplicationKeystore keystore) {
        this.keystore = keystore;
    }

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(ApplicationKeystore.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(ApplicationKeystore.class)).detachAndStopAllAppenders();
    }

    @Test
    @Order(0)
    void constructorForKeystoreInitialization_success() {
        keystore.setupApplicationKeystore();
        passInit = keystore.getPassword();
        userInit = keystore.getUsername();
        assertEquals(passInit.length(), userInit.length(), 24);
    }

    @Test
    @Order(1)
    void constructorForAlreadyExistingKeystore_success() {
        keystore.setupApplicationKeystore();
        String pass = keystore.getPassword();
        String user = keystore.getUsername();
        assertEquals(pass.length(), user.length(), 24);
        assertEquals(user, userInit);
        assertEquals(pass, passInit);
    }

    @Test
    void constructorException_fail() {
        IMockIOsFolderFactory iOsFolderFactoryMocked = mock(IMockIOsFolderFactory.class);
        when(iOsFolderFactoryMocked.getOsDataFolderPath()).thenThrow(new RuntimeException(new Exception("██ Exception")));
        keystore.setOsFolderFactoryForTests(iOsFolderFactoryMocked);
        keystore.setupApplicationKeystore();
        verify(iOsFolderFactoryMocked).getOsDataFolderPath();
        assert (logWatcher.list.get(1).getFormattedMessage()).contains("██ Exception");
    }
}
