package fr.softsf.sudofx2024.utils.os;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

public final class OsInfo {
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    /**
     * Private constructor to prevent class instantiation
     */
    @ExcludedFromCoverageReportGenerated
    private OsInfo() {
    }
}
