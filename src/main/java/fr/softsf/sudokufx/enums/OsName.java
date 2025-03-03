package fr.softsf.sudokufx.enums;

/**
 * Utility enum for operating system names.
 */
public enum OsName {
    OS_NAME(System.getProperty("os.name").toLowerCase()),
    WRONG_OS_FOR_TESTS("wrongOs");

    private final String os;

    OsName(final String os_) {
        os = os_;
    }

    public final String getOs() {
        return os;
    }
}
