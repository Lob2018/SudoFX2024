package fr.softsf.sudokufx.configuration.database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

class SecretKeyEncryptionServiceAESGCMUTest {

    private static IEncryptionService iSecretKeyEncryptionServiceAESGCM;
    private static IEncryptionService iSecretKeyEncryptionServiceAESGCMNullSecretKey;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeAll
    static void setupAll() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey symmetricKey = keyGen.generateKey();
        iSecretKeyEncryptionServiceAESGCM = spy(new SecretKeyEncryptionServiceAESGCM(symmetricKey));
        iSecretKeyEncryptionServiceAESGCMNullSecretKey = spy(new SecretKeyEncryptionServiceAESGCM(null));
    }

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class)).detachAndStopAllAppenders();
    }

    @Test
    void givenSecret_whenEncryptAndDecrypt_thenOriginalSecretReturned() {
        String secret = "Secret";
        String encrypted = iSecretKeyEncryptionServiceAESGCM.encrypt(secret);
        String decrypted = iSecretKeyEncryptionServiceAESGCM.decrypt(encrypted);
        assertEquals(secret, decrypted);
    }

    @Test
    void givenInvalidOriginalText_whenEncrypt_thenExceptionLogged() {
        iSecretKeyEncryptionServiceAESGCMNullSecretKey.encrypt("_");
        verify(iSecretKeyEncryptionServiceAESGCMNullSecretKey).encrypt("_");
        assert (logWatcher.list.getLast().getFormattedMessage()).contains("██ Exception catch inside encrypt");
    }

    @Test
    void givenInvalidCipherText_whenDecrypt_thenExceptionLogged() {
        iSecretKeyEncryptionServiceAESGCMNullSecretKey.decrypt("_");
        verify(iSecretKeyEncryptionServiceAESGCMNullSecretKey).decrypt("_");
        assert (logWatcher.list.getLast().getFormattedMessage()).contains("██ Exception catch inside decrypt(cypher)");
    }
}
