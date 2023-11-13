package com.ndonald.towergame.controllers;

import com.ndonald.towergame.models.WalkAnimation;
import com.ndonald.towergame.models.Arena;
import com.ndonald.towergame.models.BasicEnemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    @FXML
    public AnchorPane anchor;
    static final public int ARENA_WIDTH = 480;
    static final public int ARENA_HEIGHT = 480;
    static final public int GRID_WIDTH = 40;
    static final public int GRID_HEIGHT = 40;
    int enemyX, enemyY = 200;
    Timer enemyTimer;
    Arena arena;

    public GameController(){
        arena = new Arena();
        enemyTimer = new Timer();
        enemyTimer.scheduleAtFixedRate(
                new TimerTask() {
            public void run() {
                Platform.runLater(() -> createEnemy());
            }
        }, 5000,10000);
    }

    void createEnemy(){
        BasicEnemy enemy = new BasicEnemy(new WalkAnimation(BasicEnemy.sprites, 0.2F),enemyX,enemyY);
        arena.addEnemy(enemy);
        drawEnemy(enemy.getX(),enemy.getY(),enemy);
    }

    void drawEnemy(int x, int y, BasicEnemy enemy){
        ImageView imageView = new ImageView(enemy.animation.getKeyFrame(enemy.walkingTime, WalkAnimation.ANIMATION_LOOPING));
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
        transition.setDuration(Duration.seconds(60));
        transition.setPath(path);
        transition.setCycleCount(1);
        transition.play();
    }

    public void onSettingsButtonClick(MouseEvent mouseEvent) {
    }
}
