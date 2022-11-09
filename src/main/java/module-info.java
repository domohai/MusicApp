module com.example.musicapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    
    opens com.example.musicapp to javafx.fxml;
    opens com.example.musicapp.model to javafx.base;
    exports com.example.musicapp;
}