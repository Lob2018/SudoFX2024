package fr.softsf.sudokufx.interfaces;

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
