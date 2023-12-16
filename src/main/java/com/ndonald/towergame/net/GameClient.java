package com.ndonald.towergame.net;

import com.ndonald.towergame.controllers.GameController;
import com.ndonald.towergame.controllers.LobbyController;
import com.ndonald.towergame.controllers.MainPageController;
import com.ndonald.towergame.models.BasicTower;
import com.ndonald.towergame.models.Observable;
import com.ndonald.towergame.models.Player;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Observable game;

    public GameClient(Observable game, String ipAddress) {
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
            try {
                this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void parsePacket(byte[] data, InetAddress ipAddress, int port) throws IOException {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(Integer.parseInt(message.substring(0,2)));
        Packet packet = null;
        switch(type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                Player player = new Player(((Packet00Login) packet).getUsername(), ipAddress,port);
                handleLogin((Packet00Login) packet, ipAddress, port);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + ipAddress.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
                game.removePlayer(((Packet01Disconnect) packet).getUsername());
                break;
            case TOWER:
                packet = new Packet02Tower(data);
                handleTower((Packet02Tower) packet);
                break;
            case START:
                ((LobbyController)game).startGame();
                break;
            case CHAT:
                packet = new Packet04Chat(data);
                handleChat((Packet04Chat)packet);
                break;
            case POINT:
                try {
                    ((GameController) game).points = Integer.parseInt(message.substring(2));
                }
                catch(ClassCastException e) { }
                break;
        }
    }

    private void handleChat(Packet04Chat packet) {
        ((GameController)game).displayChat(packet.getMsg());
    }

    private void handleTower(Packet02Tower packet) {
        ((GameController)game).createTower(new BasicTower(packet.getTowerX(),packet.getTowerY()));
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                + " has joined the game...");
        Player player = new Player(packet.getUsername(), address, port);
        game.addEntity(player);
    }

    public void setObservable(Observable c){
        game = c;
    }
}
