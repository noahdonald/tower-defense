package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.controllers.LobbyController;
import javafx.animation.AnimationTimer;

public class LobbyTimer extends AnimationTimer {
    LobbyController c;

    public LobbyTimer(LobbyController _c) {
        c = _c;
    }

    @Override
    public void handle(long l) {

    }


}