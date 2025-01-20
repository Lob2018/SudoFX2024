package fr.softsf.sudokufx.utils.database.keystore;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.interfaces.IEncryptionService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the ApplicationKeystore.IEncryptionService interface
 * using AES-GCM (Galois/Counter Mode) encryption.
 */
@Slf4j
public final class SecretKeyEncryptionServiceAESGCM implements IEncryptionService {
    private final SecretKey secretKey;
    private Cipher cipher;
    private static final SecureRandom random = new SecureRandom();

    /**
     * Constructor for the SecretKeyEncryptionServiceAESGCM.
     * Initializes the cipher with AES/GCM/NoPadding algorithm.
     *
     * @param secretKeyP The SecretKey to be used for encryption and decryption
     */
    @ExcludedFromCoverageReportGenerated
    public SecretKeyEncryptionServiceAESGCM(final SecretKey secretKeyP) {
        secretKey = secretKeyP;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("██ Exception catch inside SecretKeyEncryptionServiceAESGCM(SecretKey) constructor : {}", e.getMessage(), e);
        }
    }

    /**
     * Encrypts the given string using AES-GCM encryption.
     *
     * @param original The string to be encrypted
     * @return A Base64 encoded string containing the encrypted data and IV, separated by '#'
     */
    @Override
    public String encrypt(@NotBlank final String original) {
        byte[] iv = new byte[16];
        try {
            random.nextBytes(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            byte[] encryptedData = cipher.doFinal(original.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            String encrypt64 = encoder.encodeToString(encryptedData);
            String iv64 = encoder.encodeToString(iv);
            return encrypt64 + "#" + iv64;
        } catch (Exception e) {
            log.error("██ Exception catch inside encrypt(original) : {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * Decrypts the given cipher text using AES-GCM decryption.
     *
     * @param cypher The Base64 encoded string containing the encrypted data and IV, separated by '#'
     * @return The decrypted string, or an empty string if decryption fails
     */
    @Override
    public String decrypt(@NotBlank final String cypher) {
        try {
            String[] split = cypher.split("#");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cypherText = decoder.decode(split[0]);
            byte[] iv = decoder.decode(split[1]);
            GCMParameterSpec paraSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paraSpec);
            byte[] decryptedData = cipher.doFinal(cypherText);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("██ Exception catch inside decrypt(cypher) : {}", e.getMessage(), e);
            return "";
        }
    }
}
