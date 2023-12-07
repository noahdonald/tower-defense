package com.ndonald.towergame.models;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicEnemy {
    protected int hp = 25 ;

    protected int speed = 3;

    protected int x, y;

    protected boolean isMoving = true;

    public float walkingTime = 0;

    public Node n;
    public ImageView v;

    public static Image[] sprites = {
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_000.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_001.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_002.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_003.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_004.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_005.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_006.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_007.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_008.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_009.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_010.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_011.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_012.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_013.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_014.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_015.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_016.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_017.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_018.png"),
                new Image("com/ndonald/towergame/assets/sprites/monster-enemy-game-sprites/PNG/1/1_enemies_1_walk_019.png")
    };

    public WalkAnimation animation;

    public BasicEnemy(WalkAnimation _animation, int _x, int _y){
        animation = _animation;
        x = _x;
        y = _y;
    }

    public void update(float deltaTime){
        x += speed * (deltaTime / 1000.0);
        walkingTime += deltaTime ;
    }

    public void setHp(int _hp) {
        hp = _hp;
        if(hp<0) {
            hp = 0;
        }
    }

    public void setSpeed(int _speed) {
        speed = _speed;
        if(speed<0) {
            speed = 0;
        }
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean _isMoving) {
        isMoving = _isMoving;
    }

    public void setNode(Node n){
        this.n = n;
    }

    public Node getNode(){
        return n;
    }

    public void setView(ImageView v){
        this.v = v;
    }

    public ImageView getView(){
        return v;
    }
}

