package com.ndonald.towergame.controllers;
import com.ndonald.towergame.Main;
import com.ndonald.towergame.models.Observable;
import com.ndonald.towergame.models.Player;
import com.ndonald.towergame.net.GameClient;
import com.ndonald.towergame.net.GameServer;
import com.ndonald.towergame.net.Packet00Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


public class MainPageController extends Observable {
    @FXML
    public AnchorPane mainRoot;
    public TextField name;
    public ImageView image;
    Player player;

    GameClient socketClient = new GameClient(this,"localhost");
    GameServer socketServer;

    @FXML
    public void onPlayButtonClick(MouseEvent mouseEvent) throws IOException {
        showStage();
    }

    @FXML
    public void onSettingsButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/SettingsPage.fxml"));
        AnchorPane pane = fxmlLoader.load();
        mainRoot.getChildren().setAll(pane);
    }
    @FXML
    public void showStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/UsernamePopup.fxml"));
        AnchorPane pane = fxmlLoader.load();
        mainRoot.getChildren().setAll(pane);
    }

    public void OnConfirmClick(MouseEvent mouseEvent) throws IOException {
        if (!name.getText().isEmpty()) {
            if (socketServer != null){
                socketServer.start();
            }
            socketClient.start();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/LobbyPage.fxml"));
            AnchorPane pane = fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            player = new Player(name.getText(),null,-1);

            Packet00Login loginPacket = new Packet00Login(player.getUsername());
            if (socketServer != null){
                socketServer.addConnection(player,loginPacket);
                controller.setServer(socketServer);
                socketServer.setObservable(controller);
            }
            loginPacket.writeData(socketClient);

            controller.setPlayer(player);
            controller.setClient(socketClient);
            socketClient.setObservable(controller);
            mainRoot.getChildren().setAll(pane);
        }
    }



    public void OnServerButtonClick(MouseEvent mouseEvent) {
        if (socketServer == null) {
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_on.png"));
            socketServer = new GameServer(this);
        } else{
            image.setImage(new Image("com/ndonald/towergame/assets/td-gui/PNG/settings/button_off.png"));
            socketServer = null;
        }
    }


    @Override
    public String getUsername() {
        return player.getUsername();
    }

    @Override
    public GameClient getClient() {
        return socketClient;
    }
}
