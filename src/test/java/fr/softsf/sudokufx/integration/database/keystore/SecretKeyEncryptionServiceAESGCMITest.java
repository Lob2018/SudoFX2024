package fr.softsf.sudokufx.integration.database.keystore;

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
import fr.softsf.sudokufx.utils.database.keystore.SecretKeyEncryptionServiceAESGCM;

class SecretKeyEncryptionServiceAESGCMITest {

    private static SecretKeyEncryptionServiceAESGCM secretKeyEncryptionServiceAESGCM;
    private static SecretKeyEncryptionServiceAESGCM secretKeyEncryptionServiceAESGCMNullSecretKey;
    private static SecretKeyEncryptionServiceAESGCM secretKeyEncryptionServiceAESGCMIncorrectSecretKey;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeAll
    static void setupAll() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey symmetricKey = keyGen.generateKey();
        secretKeyEncryptionServiceAESGCM = spy(new SecretKeyEncryptionServiceAESGCM(symmetricKey));
        secretKeyEncryptionServiceAESGCMNullSecretKey = spy(new SecretKeyEncryptionServiceAESGCM(null));
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
    void encryptDecrypt_success() {
        String secret = "Secret";
        String encrypted = secretKeyEncryptionServiceAESGCM.encrypt(secret);
        String decrypted = secretKeyEncryptionServiceAESGCM.decrypt(encrypted);
        assertEquals(secret, decrypted);
    }

    @Test
    void encryptWithNullSecretKey_fail() {
        secretKeyEncryptionServiceAESGCMNullSecretKey.encrypt("_");
        verify(secretKeyEncryptionServiceAESGCMNullSecretKey).encrypt("_");
        assert (logWatcher.list.get(logWatcher.list.size() - 1).getFormattedMessage()).contains("██ Exception catch inside encrypt");
    }

    @Test
    void decryptWithLessThanTwoBytesCypher_fail() {
        secretKeyEncryptionServiceAESGCMNullSecretKey.decrypt("_");
        verify(secretKeyEncryptionServiceAESGCMNullSecretKey).decrypt("_");
        assert (logWatcher.list.get(logWatcher.list.size() - 1).getFormattedMessage()).contains("██ Exception catch inside decrypt(cypher)");
    }
}
