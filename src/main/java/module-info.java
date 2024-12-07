module com.example.universitymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens com.example.universitymanagementsystem to javafx.fxml;
    exports com.example.universitymanagementsystem;
}