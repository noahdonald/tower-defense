package com.ndonald.towergame.models;

import com.ndonald.towergame.controllers.GameController;

import java.net.InetAddress;

public class Player {
    public String username;
    public InetAddress ipAddress;
    public int port;
    public GameController controller;

    public Player(String username, InetAddress address, int port){
        this.username = username;
        ipAddress = address;
        this.port = port;
    }

    public String getUsername(){
        return username;
    }
    public void setController(GameController c){
        controller = c;
    }

    public GameController getController(){
        return controller;
    }
}
