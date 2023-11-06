package com.ndonald.towergame.controllers;

import com.ndonald.towergame.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingsPageController {
    public AnchorPane settingsPane;

    public void OnButtonClick(MouseEvent mouseEvent) {
        //This doesn't work
        ImageView image = (ImageView)mouseEvent.getSource();
        if (Objects.equals(image.getImage().getUrl(), "@assets/td-gui/PNG/settings/button_on.png")){
            image.setImage(new Image("@assets/td-gui/PNG/settings/button_off.png"));
        }
        else{
            image.setImage(new Image("@assets/td-gui/PNG/settings/button_on.png"));
        }
    }

    public void OnExitClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/MainPage.fxml"));
        AnchorPane pane = fxmlLoader.load();
        settingsPane.getChildren().setAll(pane);
    }
}
