module lab11.laboratoria12 {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab11.laboratoria12 to javafx.fxml;
    exports lab11.laboratoria12;
}