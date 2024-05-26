package fr.softsf.sudofx2024.utils.database.keystore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.UUID;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.utils.database.GenerateSecret;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Configuration
public final class ApplicationKeystore {

    private static final String KEYSTORE_PASSWORD_FROM_UUID = String.valueOf(UUID.nameUUIDFromBytes(System.getProperty("user.name").getBytes()));
    private static final String KEYSTORE_TYPE = "pkcs12";
    private String keystoreFilePath;
    private KeyStore ks;
    private static final char[] pwdArray = KEYSTORE_PASSWORD_FROM_UUID.toCharArray();
    private static final String SYMMETRIC_KEY_ALIAS = "db-encryption-secret";
    private static final String USERNAME_ALIAS = "db-user-alias";
    private static final String PASS_ALIAS = "db-pass-alias";
    private IEncryptionService iEncryptionService;
    private String username;
    private String password;

    @Autowired
    public ApplicationKeystore(final WindowsFolderFactory iOsFolderFactory) {
        log.info("\n▓▓ ApplicationKeystore starts");
        try {
            ks = KeyStore.getInstance(KEYSTORE_TYPE);
            keystoreFilePath = iOsFolderFactory.getOsDataFolderPath() + "/SudokuFX2024KeyStore.p12";
            createOrUpdateKeystore();
            loadKeyStore();
            symmetricKey();
            credentials(USERNAME_ALIAS);
            credentials(PASS_ALIAS);
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside ApplicationKeystore constructor : %s", e.getMessage()), e);
        }
        log.info("\n▓▓ ApplicationKeystore is ready");
    }

    @ExcludedFromCoverageReportGenerated
    private void createOrUpdateKeystore() {
        try (FileOutputStream fos = new FileOutputStream(keystoreFilePath, true)) {
            ks.load(null, pwdArray);
            ks.store(fos, pwdArray);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            log.error(String.format("██ Exception catch inside createOrUpdateKeystore() : %s", e.getMessage()), e);
        }
    }

    @ExcludedFromCoverageReportGenerated
    private void loadKeyStore() {
        try (FileInputStream fileInputStream = new FileInputStream(keystoreFilePath)) {
            ks.load(fileInputStream, pwdArray);
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside loadKeyStore() - JVM doesn't support type OR password is wrong : %s", e.getMessage()), e);
        }
    }

    @ExcludedFromCoverageReportGenerated
    private void symmetricKey() {
        try {
            if (!ks.containsAlias(SYMMETRIC_KEY_ALIAS)) {
                symmetricKeyIsInKeystore();
            } else {
                symmetricKeyIsNotInKeystore();
            }
        } catch (KeyStoreException e) {
            log.error(String.format("██ Exception catch inside symmetricKey/ks.containsAlias(SYMMETRIC_KEY_ALIAS) : %s", e.getMessage()), e);
        }
    }

    @ExcludedFromCoverageReportGenerated
    private void symmetricKeyIsNotInKeystore() {
        try {
            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) ks.getEntry(SYMMETRIC_KEY_ALIAS, new KeyStore.PasswordProtection(pwdArray));
            if (entry != null) {
                iEncryptionService = new SecretKeyEncryptionServiceAESGCM(((KeyStore.SecretKeyEntry) entry).getSecretKey());
            }
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            log.error(String.format("██ Exception catch inside symmetricKeyIsNotInKeystore/ks.getEntry(SYMMETRIC_KEY_ALIAS : %s", e.getMessage()), e);
        }
    }

    @ExcludedFromCoverageReportGenerated
    private void symmetricKeyIsInKeystore() {
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

    @ExcludedFromCoverageReportGenerated
    private static void writeTheKeystore(final KeyStore ks, final String keystoreFileName, final char[] pwdArray) {
        try (FileOutputStream fos = new FileOutputStream(keystoreFileName)) {
            ks.store(fos, pwdArray);
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside writeTheKeystore/fos : %s", e.getMessage()), e);
        }
    }

    @Bean
    public String getUsername() {
        return username;
    }
    @Bean
    public String getPassword() {
        return password;
    }

    public interface IEncryptionService {
        String encrypt(String original);

        String decrypt(String cypher);
    }

}

