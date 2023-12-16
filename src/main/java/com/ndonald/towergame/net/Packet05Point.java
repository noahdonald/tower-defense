package com.ndonald.towergame.net;

public class Packet05Point extends Packet{
    private String username;
    public Packet05Point(byte[] data){
        super(05);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
    }
    public Packet05Point(String username){
        super(05);
        this.username = username;
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
        return ("05" + this.username).getBytes();
    }

    public String getUsername(){
        return username;
    }
}