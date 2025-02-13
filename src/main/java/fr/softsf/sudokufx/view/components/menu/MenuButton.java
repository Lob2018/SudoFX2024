package fr.softsf.sudokufx.view.components.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * A customizable menu button with a Material icon and text.
 * This button displays an icon (using Material icons) alongside a text label.
 */
public class MenuButton extends Button {

    /**
     * Creates a new MenuButton with the specified icon and text.
     *
     * @param icon The Material icon character to display.
     * @param text The text label for the button.
     */
    public MenuButton(String icon, String text) {
        // Passer font color, background color (en setter aussi)
        Text i = new Text(icon);
        Text t = new Text(text);
        i.getStyleClass().add("material");
        t.getStyleClass().add("root");
        HBox hbox = new HBox(i, t);
        // t.getStyleClass().add("menu-button-default"); // Uncomment to apply an additional style
        super.setGraphic(hbox);
    }
}

