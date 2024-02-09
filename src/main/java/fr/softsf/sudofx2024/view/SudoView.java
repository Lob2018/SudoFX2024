package fr.softsf.sudofx2024.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SudoView {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}