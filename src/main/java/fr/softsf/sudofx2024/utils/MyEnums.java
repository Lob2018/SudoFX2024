package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;

public class MyEnums {
    private static final String OS_NAME_ = System.getProperty("os.name").toLowerCase();
    private static final String USER_HOME_ = System.getProperty("user.home").replace("\\", "/");

    @ExcludedFromCoverageReportGenerated
    private MyEnums() {
    }

    public enum Os {
        OS_NAME(OS_NAME_);
        private final String path;

        Os(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public enum Logo {
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
        private final String path;

        Logo(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public enum Paths {
        RESOURCES_FXML_PATH("/fr/softsf/sudofx2024/fxml/"),
        RESOURCES_CSS_PATH("/fr/softsf/sudofx2024/style/style.css"),
        I18N_PATH("fr/softsf/sudofx2024/i18n/resource"),
        DATABASE_MIGRATION_PATH("/fr/softsf/sudofx2024/flyway/scripts/hsqldb/migration"),
        BASE_PATH("/AppData/Local/Soft64.fr/SudokuFX"),
        LOGO_SUDO_PNG_PATH("/fr/softsf/sudofx2024/images/Sudoku.png"),
        CONFIG_LOGBACK_PATH("/fr/softsf/sudofx2024/config/logback.xml"),
        CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS("/invalid/path"),
        USER_HOME(USER_HOME_),
        SUDO_FX_BASE_PATH(USER_HOME.getPath() + BASE_PATH.getPath()),
        SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(SUDO_FX_BASE_PATH.getPath() + "/data-sudofx"),
        SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(SUDO_FX_BASE_PATH.getPath() + "/logs-sudofx");

        private final String path;

        Paths(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
