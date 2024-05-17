package fr.softsf.sudofx2024.utils.database.keystore;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Slf4j
public final class SecretKeyEncryptionServiceAESGCM implements ApplicationKeystore.IEncryptionService {
    private final SecretKey secretKey;
    private Cipher cipher;
    private static final SecureRandom random = new SecureRandom();

    @ExcludedFromCoverageReportGenerated
    public SecretKeyEncryptionServiceAESGCM(final SecretKey secretKey) {
        this.secretKey = secretKey;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error(String.format("██ Exception catch inside SecretKeyEncryptionServiceAESGCM(SecretKey) constructor : %s", e.getMessage()), e);
        }
    }

    @Override
    public String encrypt(@NotBlank final String original) {
        byte[] iv = new byte[16]; // Initialization Vector
        try {
            random.nextBytes(iv); // Generate a random IV
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            byte[] encryptedData = cipher.doFinal(original.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            String encrypt64 = encoder.encodeToString(encryptedData);
            String iv64 = encoder.encodeToString(iv);
            return encrypt64 + "#" + iv64;
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside encrypt(original) : %s", e.getMessage()), e);
            return "";
        }
    }

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
            log.error(String.format("██ Exception catch inside decrypt(cypher) : %s", e.getMessage()), e);
            return "";
        }
    }
}
