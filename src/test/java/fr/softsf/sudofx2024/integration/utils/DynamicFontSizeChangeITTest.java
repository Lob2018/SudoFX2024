package fr.softsf.sudofx2024.integration.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import fr.softsf.sudofx2024.utils.DynamicFontSize;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


@ExtendWith(ApplicationExtension.class)
class DynamicFontSizeChangeITTest {

    private DynamicFontSize dynamicFontSize;
    private Stage stage;

    @Start
    private void start(Stage primarystage) throws IOException {
        stage = primarystage;
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(new VBox(), 500, 500);
        stage.setScene(scene);
        dynamicFontSize = new DynamicFontSize(scene);
        stage.show();
    }

    @Test
    void testUpdateFontSizeOnResize(FxRobot robot) {
        robot.interact(() -> {
            stage.setWidth(300);
            stage.setHeight(350);
        });
        assertEquals(6.57, dynamicFontSize.getCurrentFontSize(), 0.01);
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
