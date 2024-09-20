package org.plavsic.chatapplication.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        if(client.isConnected()){
            System.out.println("Client: " + client + " has connected from Thread: " + this.getName());
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;

                while ((message = bufferedReader.readLine()) != null){
                    handleBroadcast(message);
                }

            } catch (IOException e) {
                System.out.println("There was an error while reading data from input stream " + e.getMessage());
            }

        }
    }

    private void handleBroadcast(String message){
        if(message.contains("disconnect")){
            ChatServer.removeClient(this);
        }else{
            ChatServer.broadcastMessage(this,message);
        }

    }

    public void sendMessage(String message){
        // Logic for sending message to other clients
        try {
            PrintWriter printWriter = new PrintWriter(client.getOutputStream(),true);
            printWriter.println(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}