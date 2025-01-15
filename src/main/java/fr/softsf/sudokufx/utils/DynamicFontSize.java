package fr.softsf.sudokufx.utils;

import javafx.scene.Scene;
import lombok.Getter;

/**
 * Manages dynamic font sizing based on the dimensions of a JavaFX Scene. This
 * class automatically adjusts the font size of the Scene's root node when the
 * Scene's dimensions change.
 */
public final class DynamicFontSize {

    private final Scene scene;

    /**
     * The current font size calculated based on the Scene's dimensions.
     */
    @Getter
    private double currentFontSize;

    /**
     * Constructs a DynamicFontSize instance and initializes Scene listeners.
     *
     * @param theScene The JavaFX Scene to which dynamic font sizing will be
     * applied.
     */
    public DynamicFontSize(final Scene theScene) {
        scene = theScene;
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Updates the font size based on the current Scene dimensions.
     * The font size is calculated as 2.19% of the smaller dimension (width or height) of the Scene.
     * This method is called automatically when the Scene's dimensions change.
     */
    public void updateFontSize() {
        currentFontSize = Math.min(scene.getWidth(), scene.getHeight()) * 0.0219;
        scene.getRoot().setStyle("-fx-font-size: " + currentFontSize + "px;");
    }
}
