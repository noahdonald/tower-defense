package com.ndonald.towergame.net;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.models.BasicTower;
import com.ndonald.towergame.models.Observable;
import com.ndonald.towergame.models.Player;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private Observable game;
    private List<Player> connectedPlayers = new ArrayList<Player>();

    public GameServer(Observable game) {
        this.game = game;
        try{
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e){
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
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress ipAddress, int port){
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
        Packet packet = null;
        switch(type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("[" + ipAddress.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login) packet).getUsername() + " has connected...");
                Player player = new Player(((Packet00Login) packet).getUsername(), ipAddress,port);
                this.addConnection(player,(Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + ipAddress.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left...");
                this.removeConnection((Packet01Disconnect) packet);
                break;
            case TOWER:
                packet = new Packet02Tower(data);
                this.handleTower((Packet02Tower)packet);
                break;
        }
    }

    private void handleTower(Packet02Tower packet) {
        if (getPlayer(packet.getUsername()) != null) {
            packet.writeData(this);
        }
    }

    public void addConnection(Player player, Packet00Login packet){
        boolean alreadyConnected = false;
        for (Player p:connectedPlayers){
            if(player.getUsername().equalsIgnoreCase(p.getUsername())){
                if (p.ipAddress == null){
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1){
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else{
                sendData(packet.getData(), p.ipAddress, p.port);
                packet = new Packet00Login(p.getUsername());
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected){
            this.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public Player getPlayer(String username) {
        for (Player player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerIndex(String username) {
        int index = 0;
        for (Player player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (Player p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }


    public void setObservable(Observable c){
        game = c;
    }
}
