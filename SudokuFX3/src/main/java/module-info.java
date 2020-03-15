// rename com.demo.sudokufx3 to sudokufx
module sudokufx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.demo.sudokufx3 to javafx.fxml;
    // "exports com.demo;" in IntelliJ
    exports com.demo.sudokufx3;
}