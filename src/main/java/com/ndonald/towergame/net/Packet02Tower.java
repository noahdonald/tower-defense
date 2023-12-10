package com.ndonald.towergame.net;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.models.BasicEnemy;
import com.ndonald.towergame.models.BasicTower;

import java.util.Arrays;

public class Packet02Tower extends Packet{
    String username;
    GameController game;
    int towerX;
    int towerY;
    public Packet02Tower(GameController controller,String t) {
        super(02);
        game = controller;
        username = game.getUsername();
        towerX = Integer.parseInt(t.split(",")[0]);
        towerY = Integer.parseInt(t.split(",")[1]);
    }

    public Packet02Tower(byte[] data) {
        super(02);
        String d = readData(data);
        String[] dataArray = d.split(",");
        username = dataArray[0];
        towerX = Integer.parseInt(dataArray[1]);
        towerY = Integer.parseInt(dataArray[2]);
    }

    public Packet02Tower(int packetId) {
        super(packetId);
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.towerX + "," + this.towerY).getBytes();
    }

    public String getUsername(){
        return username;
    }

    public int getTowerX() {
        return towerX;
    }
    public int getTowerY() {
        return towerY;
    }
}
