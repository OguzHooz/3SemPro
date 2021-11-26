module com.example.sempro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sempro to javafx.fxml;
    exports com.example.sempro;
}