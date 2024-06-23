package fr.softsf.sudofx2024.utils;

import javafx.scene.Scene;
import lombok.Getter;

public final class DynamicFontSize {
    private final Scene scene;
    @Getter
    private double currentFontSize;

    /**
     * Initialize scene listeners and bind the font size update
     *
     * @param theScene The Scene
     */
    public DynamicFontSize(final Scene theScene) {
        scene = theScene;
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Update the font size based on Scene size
     */
    private void updateFontSize() {
        currentFontSize = Math.min(scene.getWidth(), scene.getHeight()) * 0.0219;
        scene.getRoot().setStyle("-fx-font-size: " + currentFontSize + "px;");
    }
}
