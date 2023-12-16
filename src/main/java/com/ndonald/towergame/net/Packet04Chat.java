package com.ndonald.towergame.net;

public class Packet04Chat extends Packet{
    private String msg;
    public Packet04Chat(byte[] data){
        super(04);
        String[] dataArray = readData(data).split(",");
        this.msg = dataArray[0];
    }
    public Packet04Chat(String username){
        super(04);
        this.msg = username;
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
        return ("04" + this.msg).getBytes();
    }

    public String getMsg(){
        return msg;
    }
}