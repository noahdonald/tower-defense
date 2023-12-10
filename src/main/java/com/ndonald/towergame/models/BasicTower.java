package com.ndonald.towergame.models;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class BasicTower {

    protected int x, y;


    protected int attackPower;

    private int range;

    protected boolean isShot = false;

    public static int _attackPower = 3;

    public static int _range = 200;

    public Timer timer;

    public String _imagePath = "com/ndonald/towergame/assets/sprites/stone-tower-game-assets/PNG/Stones_Tower.png";
    public String _shotImagePath = "com/ndonald/towergame/assets/sprites/stone-tower-game-assets/PNG/Shot.png";

    public BasicTower(int _x, int _y) {
        x = _x;
        y = _y;

        attackPower = _attackPower;
        range = _range;
        timer = new Timer();
    }

    public boolean isInRange(BasicEnemy m) {
        return range > Math.sqrt(Math.pow(x-m.getX(), 2)+Math.pow(y-m.getY(), 2));
    }

    public boolean isInRange(int _x, int _y) {
        return range > Math.sqrt(Math.pow(x-_x, 2)+Math.pow(y-_y, 2));
    }

    public boolean shoot(BasicEnemy m) {
        if (!isShot)
            m.setHp(m.getHp() - attackPower);

        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getRange() {
        return range;
    }

    public String getTowerType() {
        return "Basic Tower";
    }

    public boolean getIsShot() {
        return isShot;
    }

    public void setIsShot(boolean _isShot) {
        isShot = _isShot;
    }

    @Override
    public String toString() {
        return  x + "," + y;
    }
}
