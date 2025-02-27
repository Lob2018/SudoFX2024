package fr.softsf.sudokufx.configuration.database.keystore;

/**
 * Interface defining methods to encrypt and decrypt
 * using AES-GCM (Galois/Counter Mode) encryption.
 */
sealed interface IEncryptionService permits SecretKeyEncryptionServiceAESGCM {
    /**
     * Encrypts the given string using AES-GCM encryption.
     *
     * @param original The string to be encrypted
     * @return A Base64 encoded string containing the encrypted data and Initialization Vector, separated by '#'
     */
    String encrypt(String original);

    /**
     * Decrypts the given cipher text using AES-GCM decryption.
     *
     * @param cypher The Base64 encoded string containing the encrypted data and Initialization Vector, separated by '#'
     * @return The decrypted string, or an empty string if decryption fails
     */
    String decrypt(String cypher);
}
