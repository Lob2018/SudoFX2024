package fr.softsf.sudofx2024.view.components;

import fr.softsf.sudofx2024.utils.I18n;
import fr.softsf.sudofx2024.utils.MyEnums;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    @FXML
    public void addToast(String text, MyEnums.ToastLevels toastLevel) {
        Button toast = new Button(text);
        setToastStyle(toast, toastLevel);
        setAccessibility(toast, toastLevel);
        toast.setAlignment(Pos.CENTER);
        temporizeToast(toast);
        toast.setOnAction(this::toastAction);
        getChildren().add(toast);
    }

    private void setToastStyle(Button toast, MyEnums.ToastLevels toastLevel) {
        toast.getStyleClass().add("toast");
        toast.getStyleClass().add(toastLevel.getLevel());
    }

    private static void setAccessibility(Button toast, MyEnums.ToastLevels toastLevel) {
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

    private void toastAction(ActionEvent event) {
        copyText(event);
        removeToast(event);
    }

    private void copyText(ActionEvent event) {
        String text = ((Button) event.getSource()).getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void temporizeToast(Button toast) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7000), event -> getChildren().remove(toast)));
        timeline.play();
    }

    private void removeToast(ActionEvent event) {
        getChildren().remove((Node) event.getSource());
    }
}
