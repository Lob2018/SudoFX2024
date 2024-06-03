package fr.softsf.sudofx2024.view;

import fr.softsf.sudofx2024.SudoMain;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.MyEnums;
import fr.softsf.sudofx2024.viewmodel.FullMenuViewModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.DATABASE_MIGRATION_PATH;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.LOGO_SUDO_PNG_PATH;


/**
 * FullMenuView view without logic (not tested)
 */
public final class FullMenuView implements SudoMain.IPrimaryStageView {
    @FXML
    private Label welcomeText;
    @FXML
    private Button buttonHello;
    private Text text1 = new Text("Helloj! ");
    private Text text2 = new Text("\ue86c");
    private FullMenuViewModel fullMenuViewModel;

    @FXML
    private void initialize() {
        text1.getStyleClass().add("root");
        text2.getStyleClass().add("material");
        HBox hbox = new HBox(text1, text2);
        hbox.setStyle("-fx-background-color: #FF0000;");
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        buttonHello.setGraphic(hbox);
        buttonHello.setStyle("-fx-background-color: #00FF00;");
    }

    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        File file = new File(Objects.requireNonNull(DatabaseMigration.class.getResource(DATABASE_MIGRATION_PATH.getPath())).getFile());
        String filePath = file.getAbsolutePath();

        fullMenuViewModel =new FullMenuViewModel();

        welcomeText.setText("Coucou");


    }

    // ↓ Splashscreen related code ↓
    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.5;
    private final Stage primaryStage = new Stage();

    private void configureStage() {
        primaryStage.getIcons().add(new Image((Objects.requireNonNull(SudoMain.class.getResource(LOGO_SUDO_PNG_PATH.getPath()))).toExternalForm()));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(SudoMain.getScene());
        primaryStage.centerOnScreen();
    }

    private void maximizePrimaryStage() {
        Rectangle2D r = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(r.getMinX());
        primaryStage.setY(r.getMinY());
        primaryStage.setWidth(r.getWidth());
        primaryStage.setHeight(r.getHeight());
    }

    private void fadeIn(final Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        primaryStage.setMaximized(true);
    }

    private void showStage() {
        primaryStage.show();
    }

    @Override
    public void showPrimaryStage(final SudoMain.ISplashScreenView iSplashScreenView) {
        configureStage();
        maximizePrimaryStage();
        fadeIn(SudoMain.getScene().getRoot());
        showStage();
        iSplashScreenView.hideSplashScreen();
    }
}