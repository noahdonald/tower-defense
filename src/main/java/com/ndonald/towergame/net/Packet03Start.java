package com.ndonald.towergame.net;

public class Packet03Start extends Packet{

    public Packet03Start(byte[] data){
        super(03);

    }
    public Packet03Start(String username){
        super(03);

    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public byte[] getData() {
        return ("03").getBytes();
    }


}