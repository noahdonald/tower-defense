module com.ndonald.towergame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ndonald.towergame to javafx.fxml;
    exports com.ndonald.towergame;
    exports com.ndonald.towergame.controllers;
}