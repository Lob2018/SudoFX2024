package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

public final class MyEnums {

    @ExcludedFromCoverageReportGenerated
    private MyEnums() {
    }

    /**
     * Enum for OS name
     */
    public enum OsName {
        OS_NAME(System.getProperty("os.name").toLowerCase()),
        WRONG_OS_FOR_TESTS("wrongOs");

        private final String os;

        OsName(final String os_) {
            this.os = os_;
        }

        public final String getOs() {
            return os;
        }
    }

    /**
     * Enum for logs
     */
    public enum LogBackTxt {
        EMPTY_HIBERNATE_CONFIGURATION_EXCEPTION("Default Hibernate configuration not set. Please call getSessionFactory(final IHibernateConfiguration iHibernateConfiguration) first."),
        SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION("""      
                ██ Risk of loss of application data because database authentication is not valid:
                ██ You can say no and try restarting the application and if the problem persists, reset the application database.
                ██ In both cases the application will close
                """),
        ASCII_LOGO("""
                                
                                
                ▓▓ Entering application
                                
                         ▒▒▒▒▒                                                                                     \s
                        ▒▒▒▒▒▒▒                                                                                    \s
                        ▒▒▒▒▒▒▒▒                                                                                   \s
                        ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒                                                                         \s
                        ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒                                                                        \s
                   ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓ \s
                ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓ \s
                ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓  \s
                 ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓ \s
                   ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓\s
                       ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒                                                                         \s
                      ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒                                                                         \s
                      ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒                                                                         \s
                      ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒                                                                          \s
                       ▒▒▒▒▒
                """);
        private final String logBackMessage;

        LogBackTxt(final String logBackMessage_) {
            this.logBackMessage = logBackMessage_;
        }

        public final String getLogBackMessage() {
            return logBackMessage;
        }
    }

    /**
     * Enum for paths
     */
    public enum Paths {
        RESOURCES_FXML_PATH("/fr/softsf/sudofx2024/fxml/"),
        RESOURCES_CSS_PATH("/fr/softsf/sudofx2024/style/style.css"),
        I18N_PATH("fr/softsf/sudofx2024/i18n/resource"),
        DATABASE_MIGRATION_PATH("/fr/softsf/sudofx2024/flyway/scripts/hsqldb/migration"),
        BASE_PATH("/AppData/Local/Soft64.fr/SudokuFX"),
        LOGO_SUDO_PNG_PATH("/fr/softsf/sudofx2024/images/Sudoku.png"),
        CONFIG_LOGBACK_PATH("/fr/softsf/sudofx2024/config/logback.xml"),
        CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS("/invalid/path"),
        USER_HOME(System.getProperty("user.home").replace("\\", "/")),
        SUDO_FX_BASE_PATH(USER_HOME.getPath() + BASE_PATH.getPath()),
        SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(SUDO_FX_BASE_PATH.getPath() + "/data-sudofx"),
        SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(SUDO_FX_BASE_PATH.getPath() + "/logs-sudofx");

        private final String path;

        Paths(final String path_) {
            this.path = path_;
        }

        public final String getPath() {
            return path;
        }
    }
}
