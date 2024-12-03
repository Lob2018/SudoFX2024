package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import javafx.stage.Screen;

/**
 * Utility class containing various enums used throughout the application.
 */
public final class MyEnums {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    @ExcludedFromCoverageReportGenerated
    private MyEnums() {
    }

    /**
     * Enum for screen minimum size
     */
    public enum ScreenSize {
        DISPOSABLE_SIZE(Math.min(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));

        private final double size;

        ScreenSize(final double size_) {
            size = size_;
        }

        public final double getSize() {
            return size;
        }
    }

    /**
     * Enum for toast notification levels.
     */
    public enum ToastLevels {
        INFO("toast-info"),
        WARN("toast-warn"),
        ERROR("toast-error");

        private final String level;

        ToastLevels(final String level_) {
            level = level_;
        }

        public final String getLevel() {
            return level;
        }
    }

    /**
     * Enum for operating system names.
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

    /**
     * Enum for log messages and ASCII art.
     */
    public enum LogBackTxt {
        SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION("""      
                ██ Risk of loss of application data because database authentication is not valid:
                ██ You can say no and try restarting the application and if the problem persists, reset the application database.
                ██ In both cases the application will close
                """),
        ASCII_LOGO("""
                ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ Application entry ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
                
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
            logBackMessage = logBackMessage_;
        }

        public final String getLogBackMessage() {
            return logBackMessage;
        }
    }

    /**
     * Enum for various application-specific paths.
     */
    public enum Paths {
        RESOURCES_FXML_PATH("/fr/softsf/sudofx2024/fxml/"),
        RESOURCES_CSS_PATH("/fr/softsf/sudofx2024/style/style.css"),
        I18N_PATH("fr/softsf/sudofx2024/i18n/resource"),
        DATABASE_MIGRATION_PATH("/fr/softsf/sudofx2024/flyway/scripts/hsqldb/migration"),
        LOGO_SUDO_PNG_PATH("/fr/softsf/sudofx2024/images/Sudoku.png"),
        CONFIG_LOGBACK_PATH("/fr/softsf/sudofx2024/config/logback.xml"),
        CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS("/invalid/path"),
        USER_HOME(System.getProperty("user.home").replace("\\", "/")),
        DATABASE_NAME("sudofx2024db"),
        DATA_FOLDER("data-sudofx"),
        LOGS_FOLDER("logs-sudofx"),
        WINDOWS_BASE_PATH("/AppData/Local/Soft64.fr/SudokuFX/"),
        LINUX_BASE_PATH("/.local/share/Soft64.fr/SudokuFX/"),
        MACOS_BASE_PATH("/Library/Application Support/Soft64.fr/SudokuFX/"),
        WINDOWS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + WINDOWS_BASE_PATH.getPath()),
        LINUX_SUDO_FX_BASE_PATH(USER_HOME.getPath() + LINUX_BASE_PATH.getPath()),
        MACOS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + MACOS_BASE_PATH.getPath()),
        WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(WINDOWS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
        LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(LINUX_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
        MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(MACOS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
        WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(WINDOWS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
        LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(LINUX_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
        MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(MACOS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath());

        private final String path;

        Paths(final String path_) {
            path = path_;
        }

        public final String getPath() {
            return path;
        }
    }
}
