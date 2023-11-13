package com.ndonald.towergame.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicTower {

    protected int x, y;

    protected int attackPower;

    private int range;

    protected boolean isShot = false;

    protected ImageView imageView = null;

    public static int _attackPower = 3;

    public static int _range = 65;

    public static String _imagePath = "";

    public BasicTower(int _x, int _y) {
        x = _x;
        y = _y;

        attackPower = _attackPower;
        range = _range;

        setImageView(_imagePath);
    }

    public boolean isInRange(BasicEnemy m) {
        return range > Math.sqrt(Math.pow(x-m.getX(), 2)+Math.pow(y-m.getY(), 2));
    }

    public boolean isInRange(int _x, int _y) {
        return range > Math.sqrt(Math.pow(x-_x, 2)+Math.pow(y-_y, 2));
    }

    public boolean shoot(BasicEnemy m, Arena a) {
        if (isShot != true)
            m.setHp(m.getHp() - attackPower);

        return true;
    }

    public void setImageView(String _imagePath) {
        Image image = null;

        try {
            image = new Image(new FileInputStream(_imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (image != null) {
            imageView = new ImageView(image);
        }
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

    public ImageView getImageView() {
        return imageView;
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
}
