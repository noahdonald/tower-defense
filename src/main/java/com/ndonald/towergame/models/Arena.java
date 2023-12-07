package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Random;

public class Arena extends AnimationTimer {
    GameController c;

    public Arena(GameController _c) {
        c = _c;
    }

    @Override
    public void handle(long l) {
        c.updatePostions();
    }


}
