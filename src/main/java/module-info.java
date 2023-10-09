module com.example.versand {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.versand to javafx.fxml;
    exports com.example.versand;
}