package org.plavsic.chatapplication.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter printWriter;


    public void connectToServer(String ip,int port){
        try {
            socket = new Socket(ip,port);
            printWriter = new PrintWriter(socket.getOutputStream(),true);

        } catch (IOException e) {
            System.out.println("Failed to connect to server " + e.getMessage());
        }
    }

    public void sendMessage(String message){
        printWriter.println(message);
    }

    public String receiveMessage(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Error while trying to read from BufferedReader: " + e.getMessage());
        }
        return null;
    }


    public void closeConnection(){
        try {
            socket.close();
            printWriter.close();

        } catch (IOException e) {
            System.out.println("Error while trying to close printWriter " + e.getMessage());
        }
    }
}
