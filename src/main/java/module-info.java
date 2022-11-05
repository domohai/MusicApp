module com.example.musicapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    
    opens com.example.musicapp to javafx.fxml;
    exports com.example.musicapp;
}