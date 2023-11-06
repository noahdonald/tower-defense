package com.ndonald.towergame.controllers;

import com.ndonald.towergame.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController{
    @FXML
    public AnchorPane mainRoot;
    @FXML
    public void onButtonHover(MouseEvent mouseEvent) {
        // change cursor to pointer, resize effect??
    }

    @FXML
    public void onButtonHoverExit(MouseEvent mouseEvent) {
        // change cursor to pointer
    }

    @FXML
    public void onPlayButtonClick(MouseEvent mouseEvent){
        // start game
    }

    @FXML
    public void onSettingsButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/SettingsPage.fxml"));
        AnchorPane pane = fxmlLoader.load();
        mainRoot.getChildren().setAll(pane);
    }
}
