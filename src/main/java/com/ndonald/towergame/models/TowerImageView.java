package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;
import javafx.scene.image.ImageView;

public class TowerImageView {

    private BasicTower tower;

    private ImageView imageView;

    static int maxX = GameController.ARENA_WIDTH;

    static int maxY = GameController.ARENA_HEIGHT;

    static int stepX = GameController.GRID_WIDTH;

    static int stepY = GameController.GRID_HEIGHT;

    public TowerImageView(BasicTower _tower) {
        tower = _tower;
        imageView = _tower.getImageView();

        if (imageView != null) {
            imageView.setFitWidth(GameController.GRID_WIDTH);
            imageView.setFitHeight(GameController.GRID_HEIGHT);
        }
        setImageView(tower.getX(), tower.getY());

    }

    public void setImageView(int _x, int _y) {

        if (imageView != null) {

            imageView.setX(_x);
            imageView.setY(_y);

        }
    }

    public BasicTower getTower() {
        return tower;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
