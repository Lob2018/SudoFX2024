package fr.softsf.sudokufx.e2e.utils;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.TimeoutException;

import static fr.softsf.sudokufx.utils.MyEnums.ScreenSize.DISPOSABLE_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(ApplicationExtension.class)
class MyEnumsE2ETest {

    @Start
    private void start(Stage primarystage) {
        Scene scene = new Scene(new VBox(), 500, 500);
        primarystage.setScene(scene);
        primarystage.show();
    }

    @Test
    void testDISPOSABLE_SIZE() {
        assertTrue(Double.isFinite(DISPOSABLE_SIZE.getSize()));
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
