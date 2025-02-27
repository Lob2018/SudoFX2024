package fr.softsf.sudokufx.utils;

import java.util.concurrent.TimeoutException;

import static fr.softsf.sudokufx.utils.MyEnums.ScreenSize.DISPOSABLE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


@ExtendWith(ApplicationExtension.class)
class DynamicFontSizeChangeE2ETest {

    private DynamicFontSize dynamicFontSize;
    private Stage stage;

    @Start
    private void start(Stage primarystage) {
        stage = primarystage;
        Scene scene = new Scene(new VBox(), 500, 500);
        stage.setScene(scene);
        dynamicFontSize = new DynamicFontSize(scene);
        stage.show();
    }

    @Test
    void givenStageResized_whenUpdateFontSize_thenFontSizeAdjustedCorrectly(FxRobot robot) {
        robot.interact(() -> {
            stage.setWidth(300);
            stage.setHeight(350);
        });
        assertTrue(Double.isFinite(DISPOSABLE_SIZE.getSize()));
        assertEquals(6.57, dynamicFontSize.getCurrentFontSize(), 0.01);
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
