package com.ndonald.towergame.controllers;

import com.ndonald.towergame.Main;
import com.ndonald.towergame.models.BasicTower;
import com.ndonald.towergame.models.Player;
import com.ndonald.towergame.models.WalkAnimation;
import com.ndonald.towergame.models.Arena;
import com.ndonald.towergame.models.BasicEnemy;
import com.ndonald.towergame.net.GameClient;
import com.ndonald.towergame.net.GameServer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    @FXML
    public AnchorPane anchor;
    ArrayList<BasicTower> towers  = new ArrayList<BasicTower>();
    ArrayList<BasicEnemy> enemies  = new ArrayList<BasicEnemy>(0);
    int enemyX, enemyY = 200;
    Timer enemyTimer;
    Arena arena;
    Player player;
    GameServer socketServer;
    GameClient socketClient;

    public GameController(){
        arena = new Arena(this);
        arena.start();
        if (socketServer != null){
            socketServer.start();
        }
        enemyTimer = new Timer();
        enemyTimer.scheduleAtFixedRate(
                new TimerTask() {
            public void run() {
                Platform.runLater(() -> createEnemy());
            }
        }, 5000,10000);
    }

    public void checkRange(BasicTower t){
        for(BasicEnemy e:enemies){
            if (t.isInRange((int) e.getX(), (int) e.getY())){
                shoot(t,e);
                return;
            }
        }
    }

    public void updatePostions(){
        for (BasicEnemy e:enemies){
            e.setX((int) (e.getNode().getTranslateX()));
            e.setY((int) (e.getNode().getTranslateY()));
        }
    }

    void createEnemy(){
        BasicEnemy enemy = new BasicEnemy(new WalkAnimation(BasicEnemy.sprites, 0.2F),enemyX,enemyY);
        addEnemy(enemy);
        drawEnemy(enemy.getX(),enemy.getY(),enemy);
    }

    void drawEnemy(int x, int y, BasicEnemy enemy){
        ImageView imageView = new ImageView(enemy.animation.getKeyFrame(enemy.walkingTime, WalkAnimation.ANIMATION_LOOPING));
        enemy.setView(imageView);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        anchor.getChildren().addAll(imageView);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1f), ev -> {
            enemy.update(0.2f);
            imageView.setImage(enemy.animation.getKeyFrame(enemy.walkingTime, WalkAnimation.ANIMATION_LOOPING));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Path path = new Path();
        path.getElements().add(new MoveTo(-50,300));
        path.getElements().add(new LineTo(600,300));
        path.getElements().add(new LineTo(700,350));
        path.getElements().add(new LineTo(800,350));
        path.getElements().add(new LineTo(850,300));
        path.getElements().add(new LineTo(875,175));
        path.getElements().add(new LineTo(1500,150));
        PathTransition transition = new PathTransition();
        transition.setNode(imageView);
        enemy.setNode(transition.getNode());
        transition.setDuration(Duration.seconds(60));
        transition.setPath(path);
        transition.setCycleCount(1);
        transition.play();
    }

    void createTower(){
        BasicTower t = new BasicTower(420,345);
        addTower(t);
        drawTower(t);
        t.timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        Platform.runLater(() -> checkRange(t));
                    }
                }, 0,2000);
    }

    void drawTower(BasicTower t){
        ImageView imageView = new ImageView(new Image(t._imagePath));
        ImageView shotImageView = new ImageView(new Image(t._shotImagePath));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setX(t.getX());
        imageView.setY(t.getY());
        imageView.setX(t.getX());
        shotImageView.setFitWidth(25);
        shotImageView.setFitHeight(25);
        shotImageView.setX(t.getX()+25);
        shotImageView.setY(t.getY()-20);
        imageView.setPreserveRatio(true);
        anchor.getChildren().addAll(imageView,shotImageView);
    }

    void shoot(BasicTower t, BasicEnemy e) {
        ImageView shotImageView = new ImageView(new Image(t._shotImagePath));
        shotImageView.setFitWidth(25);
        shotImageView.setFitHeight(25);
        shotImageView.setPreserveRatio(true);
        anchor.getChildren().addAll(shotImageView);
        Path path = new Path();
        path.getElements().add(new MoveTo(t.getX() + 25, t.getY() - 20));
        path.getElements().add(new LineTo(e.n.getBoundsInParent().getCenterX(), e.n.getBoundsInParent().getCenterY()));
        PathTransition transition = new PathTransition();
        transition.setNode(shotImageView);
        transition.setDuration(Duration.seconds(0.3));
        transition.setPath(path);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> {
            shotImageView.setVisible(false);
            e.setHp(e.getHp()-5);
            if (e.getHp() <= 0) {
                e.getView().setVisible(false);
                enemies.remove(e);
            }
        });
        transition.play();
    }

    public void onSettingsButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/SettingsPage.fxml"));
        AnchorPane pane = fxmlLoader.load();
        anchor.getChildren().setAll(pane);
    }

    public void onTowerButtonClick(MouseEvent mouseEvent) {
        createTower();
    }

    public void addEnemy(BasicEnemy newEnemy) {
        enemies.add(newEnemy);
    }

    public void removeEnemy(BasicEnemy enemy) {
        boolean removeSuccess =  enemies.remove(enemy);
        if(!removeSuccess)
            System.out.println("ERROR: Monster Removal");
    }

    public void addTower(BasicTower newTower) {
        towers.add(newTower);
    }

    public void removeTower(BasicTower tower) {
        boolean removeSuccess =  enemies.remove(tower);
        if(!removeSuccess)
            System.out.println("ERROR: tower Removal");
    }

    public void setPlayer(Player p){
        player = p;
    }
    public void setServer(GameServer s){
        socketServer = s;
    }

    public void setClient(GameClient c){
        socketClient = c;
    }
}
