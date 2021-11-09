module com.example.maquinavirtual {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maquinavirtual to javafx.fxml;
    exports com.example.maquinavirtual;
}