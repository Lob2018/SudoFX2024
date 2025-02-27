package fr.softsf.sudokufx.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * Utility class for generating secure random numbers using Java's {@link SecureRandom} class.
 * This class provides static methods to generate random integers securely.
 * It cannot be instantiated.
 */
@Slf4j
public final class SecureRandomGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SecureRandomGenerator() {
    }

    /**
     * Generates a random integer between {@code 0} (inclusive) and the specified {@code bound} (exclusive).
     *
     * @param bound The upper limit (exclusive) for generating the random number. Must be positive.
     * @return A random integer between {@code 0} (inclusive) and {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code bound} is not positive.
     */
    public static int nextInt(int bound) {
        if (bound <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("The nextInt bound must be positive");
            log.error("██ Exception caught from nextInt(bound) : {}", e.getMessage(), e);
            throw e;
        }
        return SECURE_RANDOM.nextInt(bound);
    }

    /**
     * Generates a random integer between {@code origin} (inclusive) and {@code bound} (exclusive).
     *
     * @param origin The lower limit (inclusive) for generating the random number.
     * @param bound  The upper limit (exclusive) for generating the random number.
     * @return A random integer between {@code origin} (inclusive) and {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to {@code bound}.
     */
    public static int nextInt(int origin, int bound) {
        if (origin >= bound) {
            IllegalArgumentException e = new IllegalArgumentException("The nextInt origin must be less than bound");
            log.error("██ Exception caught from nextInt(origin,bound) : {}", e.getMessage(), e);
            throw e;
        }
        return SECURE_RANDOM.nextInt(bound - origin) + origin;
    }
}

