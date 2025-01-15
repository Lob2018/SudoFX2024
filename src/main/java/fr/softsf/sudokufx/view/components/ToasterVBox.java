package fr.softsf.sudokufx.view.components;

import fr.softsf.sudokufx.utils.I18n;
import fr.softsf.sudokufx.utils.MyEnums;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

/**
 * A custom VBox component for displaying toast notifications. This class
 * extends JavaFX's VBox and provides methods to add and manage toast messages.
 */
@Component
public class ToasterVBox extends VBox {

    /**
     * Constructor for ToasterVBox. Initializes the VBox and sets its alignment
     * to bottom center.
     */
    public ToasterVBox() {
        super();
        setAlignment(Pos.BOTTOM_CENTER);
    }

    /**
     * Adds a toast notification with a specified duration.
     *
     * @param text       The text content of the toast
     * @param toastLevel The severity level of the toast (info, warn, error)
     * @param duration   The display duration of the toast in milliseconds
     */
    @FXML
    public void addToastWithDuration(final String text, final MyEnums.ToastLevels toastLevel, final double duration) {
        final Button toast = new Button(text);
        setToastStyle(toast, toastLevel);
        setAccessibility(toast, toastLevel, text);
        toast.setAlignment(Pos.CENTER);
        temporizeToast(toast, duration);
        toast.setOnAction(this::toastActions);
        getChildren().add(toast);
    }

    /**
     * Adds a toast notification with an automatically calculated duration based
     * on text length.
     *
     * @param text       The text content of the toast
     * @param toastLevel The severity level of the toast (info, warn, error)
     */
    @FXML
    public void addToast(final String text, final MyEnums.ToastLevels toastLevel) {
        final Button toast = new Button(text);
        setToastStyle(toast, toastLevel);
        setAccessibility(toast, toastLevel, text);
        toast.setAlignment(Pos.CENTER);
        final double duration = Math.max(6000, (double) text.length() * 1000 / 5);
        temporizeToast(toast, duration);
        toast.setOnAction(this::toastActions);
        getChildren().add(toast);
    }

    /**
     * Sets the style of the toast based on its level.
     *
     * @param toast      The toast button
     * @param toastLevel The severity level of the toast
     */
    private void setToastStyle(final Button toast, final MyEnums.ToastLevels toastLevel) {
        toast.getStyleClass().add("toast");
        toast.getStyleClass().add(toastLevel.getLevel());
    }

    /**
     * Sets the accessibility text for the toast based on its level.
     * <p>
     * This method configures the accessibility text and tooltip for a toast button
     * according to the specified severity level. The tooltip will provide additional
     * information to users, enhancing the accessibility of the application.
     *
     * @param toast      The toast button to which the accessibility text and tooltip will be applied.
     * @param toastLevel The severity level of the toast, which determines the content of the accessibility text and tooltip.
     */
    private void setAccessibility(final Button toast, final MyEnums.ToastLevels toastLevel, final String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(0));
        String info = getToastInfo(toastLevel);
        toast.setAccessibleText(info + text);
        tooltip.setText(info);
        Tooltip.install(toast, tooltip);
    }

    /**
     * Retrieves the information text corresponding to the specified toast level.
     * <p>
     * This method uses a switch expression to return the appropriate message
     * based on the severity level of the toast. It fetches the localized
     * string from the I18n resource bundle.
     *
     * @param toastLevel The severity level of the toast, which can be WARN, ERROR, or INFO.
     * @return A string containing the localized message for the specified toast level.
     */
    private String getToastInfo(MyEnums.ToastLevels toastLevel) {
        return switch (toastLevel) {
            case WARN -> I18n.getValue("toastlevel.warn");
            case ERROR -> I18n.getValue("toastlevel.error");
            default -> I18n.getValue("toastlevel.info");
        };
    }

    /**
     * Handles actions when a toast is clicked. Copies the toast text to
     * clipboard and removes the toast.
     *
     * @param event The action event triggered by clicking the toast
     */
    private void toastActions(final ActionEvent event) {
        Button toast = (Button) event.getSource();
        copyToClipboard(toast);
        removeToast(toast);
    }

    /**
     * Copies the text content of a toast to the system clipboard.
     *
     * @param button The toast button whose text is to be copied
     */
    private void copyToClipboard(final Button button) {
        String text = button.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * Sets a timer to automatically remove the toast after a specified duration.
     *
     * This method ensures that the currently focused node is preserved when the toast
     * is removed, allowing for a seamless user experience. Additionally, it guarantees
     * that the narrator continues to read the toast message without interruption,
     * providing accessibility for users who rely on screen readers.
     *
     * @param toast    The toast button to be removed.
     * @param duration The duration in milliseconds after which the toast should
     *                 be removed. The toast will be removed without disrupting
     *                 the focus of the currently active node.
     */
    private void temporizeToast(final Button toast, final double duration) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> {
            Node focusedNode = this.getScene().getFocusOwner();
            getChildren().remove(toast);
            if (focusedNode != null) {
                focusedNode.requestFocus();
            }
        }));
        timeline.play();
    }

    /**
     * Removes a specific toast from the ToasterVBox.
     *
     * @param button The toast button to be removed
     */
    private void removeToast(final Button button) {
        getChildren().remove(button);
    }
}
