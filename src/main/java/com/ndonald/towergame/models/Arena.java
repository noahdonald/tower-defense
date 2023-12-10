package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;
import javafx.animation.AnimationTimer;

public class Arena extends AnimationTimer {
    GameController c;

    public Arena(GameController _c) {
        c = _c;
    }

    @Override
    public void handle(long l) {
        c.updatePostions();
        c.scoreboard.setText(Integer.toString(c.points));
//        Packet02State packet = new Packet02State(c);
//        packet.writeData(c.socketClient);
    }


}
