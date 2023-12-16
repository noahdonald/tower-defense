package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.net.Packet05Point;
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
        Packet05Point packet = new Packet05Point(Integer.toString(c.points));
        if (c.socketServer != null){
            packet.writeData(c.socketServer);
        }
    }


}
