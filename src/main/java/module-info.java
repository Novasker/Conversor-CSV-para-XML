module com.conversor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.desktop;

    opens com.conversor to javafx.fxml;
    exports com.conversor;
    exports com.conversor.Controller;
    opens com.conversor.Controller to javafx.fxml;
}