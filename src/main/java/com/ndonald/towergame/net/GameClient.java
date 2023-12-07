package com.ndonald.towergame.net;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.controllers.MainPageController;
import com.ndonald.towergame.models.Player;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private MainPageController game;

    public GameClient(MainPageController game, String ipAddress) {
        this.game = game;
        try{
            socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
    }

    public void run() {
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try{
                socket.receive(packet);
            } catch (IOException e){
                e.printStackTrace();
            }
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

        }
    }

    private void parsePacket(byte[] data, InetAddress ipAddress, int port){
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(Integer.parseInt(message.substring(0,2)));
        Packet packet = null;
        switch(type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("[" + ipAddress.getHostAddress() + ":" + port + "]" + ((Packet00Login) packet).getUsername() + "has connected...");
                Player player = new Player(((Packet00Login) packet).getUsername(), ipAddress,port);
                game.addEntity(player);
                break;
            case DISCONNECT:
                break;
        }
    }

    public void sendData(byte[] data){
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
