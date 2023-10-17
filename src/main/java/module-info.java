module com.conversor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.opencsv;

    opens com.conversor to javafx.fxml;
    exports com.conversor;
    exports com.conversor.Controller;
    opens com.conversor.Controller to javafx.fxml;
}