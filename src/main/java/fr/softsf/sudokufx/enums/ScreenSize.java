package fr.softsf.sudokufx.enums;

import javafx.stage.Screen;

/**
 * Utility enum for screen minimum size
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
