package fr.softsf.sudokufx.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;


/**
 * The SecureRandomGenerator class provides methods to generate random numbers
 * securely using Java's SecureRandom class.
 */
@Component
public class SecureRandomGenerator {

    private final SecureRandom secureRandom;

    /**
     * Constructor for the SecureRandomGenerator class.
     * Initializes an instance of SecureRandom to generate random numbers.
     */
    public SecureRandomGenerator() {
        this.secureRandom = new SecureRandom();
    }

    /**
     * Generates a random integer between 0 (inclusive) and the specified bound (exclusive).
     *
     * @param bound The upper limit (exclusive) for generating the random number.
     * @return A random integer between 0 and bound.
     */
    public int nextInt(int bound) {
        return secureRandom.nextInt(bound);
    }

    /**
     * Generates a random integer between origin (inclusive) and bound (exclusive).
     *
     * @param origin The lower limit (inclusive) for generating the random number.
     * @param bound  The upper limit (exclusive) for generating the random number.
     * @return A random integer between origin and bound.
     */
    public int nextInt(int origin, int bound) {
        return secureRandom.nextInt(bound - origin) + origin;
    }
}
