package fr.softsf.sudofx2024.utils.database.keystore;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.os.OsFolderFactoryManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.UUID;

/**
 * Manages the application's keystore for secure storage of keys and database credentials.
 */
@Slf4j
@Component
public class ApplicationKeystore implements IKeystore {

    private static final String KEYSTORE_PASSWORD_FROM_UUID = String.valueOf(UUID.nameUUIDFromBytes(System.getProperty("user.name").getBytes()));
    private static final String KEYSTORE_TYPE = "pkcs12";
    private static final char[] pwdArray = KEYSTORE_PASSWORD_FROM_UUID.toCharArray();
    private static final String SYMMETRIC_KEY_ALIAS = "db-encryption-secret";
    private static final String USERNAME_ALIAS = "db-user-alias";
    private static final String PASS_ALIAS = "db-pass-alias";
    private OsFolderFactoryManager.IOsFolderFactory osFolderFactory;
    private String keystoreFilePath;
    private KeyStore ks;
    private IEncryptionService iEncryptionService;

    @Getter
    private String username;
    @Getter
    private String password;

    public ApplicationKeystore(OsFolderFactoryManager.IOsFolderFactory osFolderFactory) {
        this.osFolderFactory = osFolderFactory;
    }

    /**
     * Write data to the keystore file
     *
     * @param ks               The Keystore
     * @param keystoreFileName The Keystore filename
     * @param pwdArray         The keystore password
     */
    @ExcludedFromCoverageReportGenerated
    private static void writeTheKeystore(final KeyStore ks, final String keystoreFileName, final char[] pwdArray) {
        try (FileOutputStream fos = new FileOutputStream(keystoreFileName)) {
            ks.store(fos, pwdArray);
        } catch (Exception e) {
            log.error("██ Exception catch inside writeTheKeystore/fos : {}", e.getMessage(), e);
        }
    }

    public void setupApplicationKeystore() {
        log.info("\n▓▓ ApplicationKeystore starts");
        try {
            ks = KeyStore.getInstance(KEYSTORE_TYPE);
            keystoreFilePath = osFolderFactory.getOsDataFolderPath() + "/SudokuFX2024KeyStore.p12";
            createOrUpdateKeystore();
            loadKeyStore();
            symmetricKey();
            credentials(USERNAME_ALIAS);
            credentials(PASS_ALIAS);
        } catch (Exception e) {
            log.error("██ Exception catch inside ApplicationKeystore setupApplicationKeystore() : {}", e.getMessage(), e);
        }
        log.info("\n▓▓ ApplicationKeystore is ready");
    }

    /**
     * Create or update the Keystore
     */
    @ExcludedFromCoverageReportGenerated
    private void createOrUpdateKeystore() {
        try (FileOutputStream fos = new FileOutputStream(keystoreFilePath, true)) {
            ks.load(null, pwdArray);
            ks.store(fos, pwdArray);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            log.error("██ Exception catch inside createOrUpdateKeystore() : {}", e.getMessage(), e);
        }
    }

    /**
     * Load the Keystore
     */
    @ExcludedFromCoverageReportGenerated
    private void loadKeyStore() {
        try (FileInputStream fileInputStream = new FileInputStream(keystoreFilePath)) {
            ks.load(fileInputStream, pwdArray);
        } catch (Exception e) {
            log.error("██ Exception catch inside loadKeyStore() - JVM doesn't support type OR password is wrong : {}", e.getMessage(), e);
        }
    }

    /**
     * Check the symmetric key presence
     */
    @ExcludedFromCoverageReportGenerated
    private void symmetricKey() {
        try {
            if (ks.containsAlias(SYMMETRIC_KEY_ALIAS)) {
                symmetricKeyIsInKeystore();
            } else {
                symmetricKeyNotInKeystore();
            }
        } catch (KeyStoreException e) {
            log.error("██ Exception catch inside symmetricKey/ks.containsAlias(SYMMETRIC_KEY_ALIAS) : {}", e.getMessage(), e);
        }
    }

    /**
     * Get the symmetric key and set encryption service
     */
    @ExcludedFromCoverageReportGenerated
    private void symmetricKeyIsInKeystore() {
        try {
            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) ks.getEntry(SYMMETRIC_KEY_ALIAS, new KeyStore.PasswordProtection(pwdArray));
            if (entry != null) {
                iEncryptionService = new SecretKeyEncryptionServiceAESGCM(entry.getSecretKey());
            }
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            log.error("██ Exception catch inside symmetricKeyIsNotInKeystore/ks.getEntry(SYMMETRIC_KEY_ALIAS :{}", e.getMessage(), e);
        }
    }

    /**
     * Set the symmetric key and set encryption service
     */
    @ExcludedFromCoverageReportGenerated
    private void symmetricKeyNotInKeystore() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, new SecureRandom());
            SecretKey symmetricKey = keyGen.generateKey();
            iEncryptionService = new SecretKeyEncryptionServiceAESGCM(symmetricKey);
            addToKeystore(SYMMETRIC_KEY_ALIAS, symmetricKey);
        } catch (NoSuchAlgorithmException e) {
            log.error("██ Exception catch inside symmetricKeyIsInKeystore/keyGen = KeyGenerator.getInstance : {}", e.getMessage(), e);
        }
    }

    /**
     * Get or set credentials by alias
     *
     * @param alias The alias to use
     */
    @ExcludedFromCoverageReportGenerated
    private void credentials(final String alias) {
        try {
            if (ks.containsAlias(alias)) {
                getCredentials(alias);
            } else {
                setCredentials(alias);
            }
        } catch (KeyStoreException e) {
            log.error("██ Exception catch inside credentials/ks.containsAlias(alias) : {}", e.getMessage(), e);
        }
    }

    /**
     * Set credential by alias
     *
     * @param alias The alias
     */
    @ExcludedFromCoverageReportGenerated
    private void setCredentials(final String alias) {
        try {
            String secret = switch (alias) {
                case USERNAME_ALIAS -> {
                    username = GenerateSecret.generatePassaySecret();
                    yield iEncryptionService.encrypt(username);
                }
                case PASS_ALIAS -> {
                    password = GenerateSecret.generatePassaySecret();
                    yield iEncryptionService.encrypt(password);
                }
                default -> "";
            };
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            addToKeystore(alias, secretKey);
        } catch (Exception e) {
            log.error("██ Exception catch inside setCredentials(alias) : {}", e.getMessage(), e);
        }
    }

    /**
     * Get credential by alias
     *
     * @param alias The alias
     */
    @ExcludedFromCoverageReportGenerated
    private void getCredentials(final String alias) {
        try {
            KeyStore.Entry entry = ks.getEntry(alias, new KeyStore.PasswordProtection(pwdArray));
            if (entry instanceof KeyStore.SecretKeyEntry secretEntry) {
                byte[] keyBytes = secretEntry.getSecretKey().getEncoded();
                String value = iEncryptionService.decrypt(new String(keyBytes, StandardCharsets.UTF_8));
                if (alias.equals(USERNAME_ALIAS)) {
                    username = value;
                } else if (alias.equals(PASS_ALIAS)) {
                    password = value;
                }
                System.out.println("GET alias - username - password - secret : " + alias + " - " + username + " - " + password);
            } else {
                log.warn("▒▒ Entry is not an instance of the Keystore");
            }
        } catch (Exception e) {
            log.error("██ Exception catch inside getCredentials(alias) : {}", e.getMessage(), e);
        }
    }

    /**
     * Add alias:secret in the Keystore
     *
     * @param alias     The alias
     * @param secretKey The secret
     */
    @ExcludedFromCoverageReportGenerated
    private void addToKeystore(final String alias, final SecretKey secretKey) {
        KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(pwdArray);
        try {
            ks.setEntry(alias, secret, entryPassword);
        } catch (KeyStoreException e) {
            log.error("██ Exception catch inside addToKeystore/ks.setEntry(alias, secret, entryPassword) : {}", e.getMessage(), e);
        }
        writeTheKeystore(ks, keystoreFilePath, pwdArray);
    }

    /**
     * Stubbing setter for OS folder factory
     *
     * @param osFolderFactoryP The OS folder factory
     */
    void setOsFolderFactoryForTests(OsFolderFactoryManager.IOsFolderFactory osFolderFactoryP) {
        osFolderFactory = osFolderFactoryP;
    }

    public interface IEncryptionService {
        /**
         * Encrypt original
         *
         * @param original The original
         * @return The encrypted original
         */
        String encrypt(String original);

        /**
         * Decrypt original
         *
         * @param cypher The cypher
         * @return The decrypted cypher
         */
        String decrypt(String cypher);
    }
}

