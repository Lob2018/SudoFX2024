package fr.softsf.sudofx2024.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.w3c.dom.events.Event;

/*
Non testée car elle ne contient pas de logique
 */
public class SudoView {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
//        Node sourceNode = (Node) event.getSource();
//        Scene currentScene = sourceNode.getScene();
//        System.out.println(currentScene.toString());

        welcomeText.setText("Welcome to JavaFX Application!");
    }
}