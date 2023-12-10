package com.ndonald.towergame.models;

import com.ndonald.towergame.net.GameClient;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    List<Player> entities = new ArrayList<Player>();

    public synchronized void removePlayer(String username) {
        int index = 0;
        for (Player p : getEntities()) {
            if (p instanceof Player && ((Player) p).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        this.getEntities().remove(index);
    }

    public synchronized List<Player> getEntities() {
        return this.entities;
    }

    public synchronized void addEntity(Player p){
        entities.add(p);
    }

    public abstract String getUsername();

    public abstract GameClient getClient();
}
