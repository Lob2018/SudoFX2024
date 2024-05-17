package fr.softsf.sudofx2024.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

public class DynamicFontSize {
    private final Scene scene;
    @Getter
    private double currentFontSize;

    public DynamicFontSize(final Scene theScene) {
        this.scene = theScene;
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    private void updateFontSize() {
        currentFontSize = Math.min(scene.getWidth(), scene.getHeight()) * 0.0219;
        scene.getRoot().setStyle("-fx-font-size: " + currentFontSize + "px;");
    }
}
