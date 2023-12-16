package com.ndonald.towergame.controllers;

import com.ndonald.towergame.Main;
import com.ndonald.towergame.models.Observable;
import com.ndonald.towergame.net.GameClient;
import com.ndonald.towergame.models.*;
import com.ndonald.towergame.net.GameServer;
import com.ndonald.towergame.net.Packet;
import com.ndonald.towergame.net.Packet03Start;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyController extends Observable {
    @FXML
    public AnchorPane mainRoot;
    Player player;
    GameClient client;
    GameServer server;
    Timer timer;
    Label waiting;
    public List<String> online;
    Observable game;
    double currentX=380;
    double currentY=160;

    public LobbyController(){
        online = new ArrayList<>();
        waiting = new Label("Connecting...");
        waiting.setLayoutX(390);
        waiting.setLayoutY(currentY+5);
        waiting.setPrefHeight(24);
        waiting.setPrefWidth(138);
        waiting.setId("waiting");
        waiting.setStyle("-fx-text-fill: #ebdf75;");
    }

    @Override
    public String getUsername() {
        return player.getUsername();
    }

    @Override
    public GameClient getClient() {
        return client;
    }

    public void addClient(Player p){
        online.add(p.getUsername());
        ImageView table = new ImageView(new Image("com/ndonald/towergame/assets/td-gui/PNG/registration/table_1.png"));
        table.setX(currentX);
        table.setY(currentY);
        table.setPreserveRatio(true);
        table.setPickOnBounds(true);
        table.setFitHeight(36);
        table.setFitWidth(423);
        Label username = new Label(p.getUsername());
        username.setLayoutX(390);
        username.setLayoutY(currentY+5);
        username.setPrefHeight(24);
        username.setPrefWidth(138);
        username.setStyle("-fx-text-fill: #ebdf75;");
        ImageView vsButton = new ImageView(new Image("com/ndonald/towergame/assets/td-gui/PNG/interface_game/button_start.png"));
        ImageView coopButton = new ImageView(new Image("com/ndonald/towergame/assets/td-gui/PNG/menu/button_play.png"));
        vsButton.setPickOnBounds(true);
        vsButton.setPreserveRatio(true);
        vsButton.setX(562);
        vsButton.setY(currentY);
        vsButton.setFitHeight(36);
        vsButton.setFitWidth(423);
        coopButton.setPickOnBounds(true);
        coopButton.setPreserveRatio(true);
        coopButton.setX(610);
        coopButton.setY(currentY);
        coopButton.setFitHeight(36);
        coopButton.setFitWidth(423);
        coopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    onCoopButton(mouseEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        vsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onVsButton(mouseEvent);
            }
        });
        mainRoot.getChildren().addAll(table,username,coopButton,vsButton);
        currentY+=40;
    }

    public void setServer(GameServer s){
        server = s;
        timer = new Timer();
        waiting.setVisible(false);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        Platform.runLater(() ->{
                            if (server != null){
                                for (Player p:server.getConnectedPlayers()){
                                    if (online == null){
                                        if (p.getUsername() != player.getUsername()) {
                                            addClient(p);
                                        }
                                    } else if (!online.contains(p.getUsername())){
                                        if (p.getUsername() != player.getUsername()) {
                                            addClient(p);
                                        }
                                    }
                                }}});
                    }
                }, 0, 1000);
    }

    public void setClient(GameClient c){
        client = c;
        if (server == null){
            mainRoot.getChildren().addAll(waiting);
        }
    }

    public void setController(Observable c){
        game = c;
    }

    public void setPlayer(Player p){
        player = p;
    }

    private void onCoopButton(MouseEvent e) throws IOException {
        Packet packet = new Packet03Start("start");
        packet.writeData(server);
    }
    private void onVsButton(MouseEvent e){
        Packet packet = new Packet03Start("start");
        packet.writeData(server);
    }

    public GameServer getServer() {
        return server;
    }

    public void startGame() throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/MainGame.fxml"));
            AnchorPane pane = null;
            try {
                pane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GameController controller = fxmlLoader.<GameController>getController();
            if (server != null) {
                controller.setServer(server);
                server.setObservable(controller);
            }
            controller.setPlayer(player);
            player.setController(controller);
            controller.setClient(client);
            client.setObservable(controller);
            mainRoot.getChildren().setAll(pane);
        });
    }
}
