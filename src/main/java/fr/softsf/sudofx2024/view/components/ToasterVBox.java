package fr.softsf.sudofx2024.view.components;

import fr.softsf.sudofx2024.utils.I18n;
import fr.softsf.sudofx2024.utils.MyEnums;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class ToasterVBox extends VBox {

    public ToasterVBox() {
        super();
        setAlignment(Pos.BOTTOM_CENTER);
    }

    /**
     * Add a toast to this ToasterVBox
     *
     * @param text       The text to show
     * @param toastLevel The toast level (info, warn, error)
     * @param duration   The toast display duration
     */
    @FXML
    public void addToast(final String text, final MyEnums.ToastLevels toastLevel, final double duration) {
        Button toast = new Button(text);
        setToastStyle(toast, toastLevel);
        setAccessibility(toast, toastLevel);
        toast.setAlignment(Pos.CENTER);
        temporizeToast(toast, duration);
        toast.setOnAction(this::toastActions);
        getChildren().add(toast);
    }

    private void setToastStyle(final Button toast, final MyEnums.ToastLevels toastLevel) {
        toast.getStyleClass().add("toast");
        toast.getStyleClass().add(toastLevel.getLevel());
    }

    private static void setAccessibility(final Button toast, final MyEnums.ToastLevels toastLevel) {
        switch (toastLevel) {
            case WARN:
                toast.setAccessibleText(I18n.getValue("toastlevel.warn"));
                break;
            case ERROR:
                toast.setAccessibleText(I18n.getValue("toastlevel.error"));
                break;
            default:
                toast.setAccessibleText(I18n.getValue("toastlevel.info"));
                break;
        }
    }

    private void toastActions(final ActionEvent event) {
        Button toast = (Button) event.getSource();
        copyToClipboard(toast);
        removeToast(toast);
    }

    private void copyToClipboard(final Button button) {
        String text = button.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void temporizeToast(final Button toast, final double duration) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> getChildren().remove(toast)));
        timeline.play();
    }

    private void removeToast(final Button button) {
        getChildren().remove(button);
    }
}
