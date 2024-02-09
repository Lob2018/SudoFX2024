package fr.softsf.sudofx2024;

import fr.softsf.sudofx2024.utils.Logs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SudoMain extends Application {
    private static final Logger logger = LogManager.getLogger(SudoMain.class);

    @Override
    public void start(final Stage primaryStage) {
        try {
            // Log4j2 initialization
            Logs.getInstance();

            String fxmlPath = "/fr/softsf/sudofx2024/fxml/sudo-view.fxml";
            Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(fxmlPath)));
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();

            throw new RuntimeException("Mon message");

        } catch (Exception e) {
            //  logger.fatal("This is a critical message : " + e.getMessage(), e);
            logger.error("Exception catch from start(Stage stage) : " + e.getMessage(), e);
        }
    }
}
