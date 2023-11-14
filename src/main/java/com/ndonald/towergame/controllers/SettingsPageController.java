package com.ndonald.towergame.controllers;

import com.ndonald.towergame.Main;
import javafx.application.Application;
import javafx.application.Platform;
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
    boolean musicOn = true;
    boolean soundsOn = true;


    public void OnMusicButtonClick(MouseEvent mouseEvent) {
        //This doesn't work
        ImageView image = (ImageView)mouseEvent.getSource();
        if (musicOn){
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_off.png"));
            musicOn = false;
        }
        else{
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_on.png"));
            musicOn = true;
            //functionality is needed later to actually add the music
        }
    }

    public void OnSoundsButtonClick(MouseEvent mouseEvent) {
        //This doesn't work
        ImageView image = (ImageView)mouseEvent.getSource();
        if (soundsOn){
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_off.png"));
            soundsOn = false;
        }
        else{
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_on.png"));
            soundsOn = true;
            //functionality is needed later to actually add the music
        }
    }

    public void OnExitClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/MainPage.fxml"));
        AnchorPane pane = fxmlLoader.load();
        settingsPane.getChildren().setAll(pane);
    }

    public void OnQuitButtonClick(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
