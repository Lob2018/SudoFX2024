package fr.softsf.sudokufx.view.components.toaster;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

/**
 * A custom button with a display text and a full detail text.
 */
@Getter
@Setter
public class ToasterButton extends Button {
    private String fullDetailText;

    /**
     * Creates a ToasterButton.
     *
     * @param text           Text displayed on the button.
     * @param fullDetailText Separate, complete detailed text.
     */
    public ToasterButton(String text, String fullDetailText) {
        super(text);
        this.fullDetailText = fullDetailText;
    }
}
