module fr.softsf.sudofx2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens fr.softsf.sudofx2024 to javafx.fxml;
    exports fr.softsf.sudofx2024;
    exports fr.softsf.sudofx2024.view;
    opens fr.softsf.sudofx2024.view to javafx.fxml;
}