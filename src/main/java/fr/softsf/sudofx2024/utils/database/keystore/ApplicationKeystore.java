package fr.softsf.sudofx2024.utils.database.keystore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.UUID;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Component
public class ApplicationKeystore implements IKeystore {
    @Autowired
    WindowsFolderFactory osFolderFactory;

    private static final String KEYSTORE_PASSWORD_FROM_UUID = String.valueOf(UUID.nameUUIDFromBytes(System.getProperty("user.name").getBytes()));
    private static final String KEYSTORE_TYPE = "pkcs12";
    private String keystoreFilePath;
    private KeyStore ks;
    private static final char[] pwdArray = KEYSTORE_PASSWORD_FROM_UUID.toCharArray();
    private static final String SYMMETRIC_KEY_ALIAS = "db-encryption-secret";
    private static final String USERNAME_ALIAS = "db-user-alias";
    private static final String PASS_ALIAS = "db-pass-alias";
    private IEncryptionService iEncryptionService;

    @Getter
    private String username;
    @Getter
    private String password;

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
            log.error(String.format("██ Exception catch inside ApplicationKeystore setupApplicationKeystore() : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside createOrUpdateKeystore() : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside loadKeyStore() - JVM doesn't support type OR password is wrong : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside symmetricKey/ks.containsAlias(SYMMETRIC_KEY_ALIAS) : %s", e.getMessage()), e);
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
                iEncryptionService = new SecretKeyEncryptionServiceAESGCM(((KeyStore.SecretKeyEntry) entry).getSecretKey());
            }
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            log.error(String.format("██ Exception catch inside symmetricKeyIsNotInKeystore/ks.getEntry(SYMMETRIC_KEY_ALIAS : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside symmetricKeyIsInKeystore/keyGen = KeyGenerator.getInstance : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside credentials/ks.containsAlias(alias) : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside setCredentials(alias) : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside getCredentials(alias) : %s", e.getMessage()), e);
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
            log.error(String.format("██ Exception catch inside addToKeystore/ks.setEntry(alias, secret, entryPassword) : %s", e.getMessage()), e);
        }
        writeTheKeystore(ks, keystoreFilePath, pwdArray);
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
            log.error(String.format("██ Exception catch inside writeTheKeystore/fos : %s", e.getMessage()), e);
        }
    }

    /**
     * Stubbing setter for OS folder factory
     *
     * @param osFolderFactoryP The OS folder factory
     */
    void setOsFolderFactoryForTests(WindowsFolderFactory osFolderFactoryP) {
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

