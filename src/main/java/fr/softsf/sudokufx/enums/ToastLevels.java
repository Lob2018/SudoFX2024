package fr.softsf.sudokufx.enums;

/**
 * Utility enum for toast notification levels.
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
