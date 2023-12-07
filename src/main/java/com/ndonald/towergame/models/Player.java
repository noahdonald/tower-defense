package com.ndonald.towergame.models;

import java.net.InetAddress;

public class Player {
    public String username;
    public InetAddress ipAddress;
    public int port;

    public Player(String username, InetAddress address, int port){
        this.username = username;
        ipAddress = address;
        this.port = port;
    }

    public String getUsername(){
        return username;
    }
}
