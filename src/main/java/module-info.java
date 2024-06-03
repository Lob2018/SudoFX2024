module fr.softsf.sudofx2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.validation;

    opens fr.softsf.sudofx2024.model to org.hibernate.orm.core;
    opens fr.softsf.sudofx2024.interfaces to org.hibernate.orm.core;

    opens fr.softsf.sudofx2024 to javafx.fxml;
    exports fr.softsf.sudofx2024;
    opens fr.softsf.sudofx2024.view to javafx.fxml;
    exports fr.softsf.sudofx2024.view;
}