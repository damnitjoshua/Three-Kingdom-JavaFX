module com.example.fxfinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fxfinal to javafx.fxml;
    exports com.example.fxfinal;
}