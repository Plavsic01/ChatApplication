package org.plavsic.chatapplication.model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    private final int SERVER_PORT = 3000;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public void startServer(){
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is created: " + serverSocket);
            System.out.println("Waiting for connection...");

            while (true){
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }

        } catch (IOException e) {
            System.out.println("There was an error while starting a server! " + e.getMessage());
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("There was an error while trying to close server connection " + e.getMessage());
            }
        }
    }

    public static void broadcastMessage(ClientHandler senderClient,String message){
        clients.forEach(client -> {
            if(client != senderClient){
                client.sendMessage(message);
            }
        });
    }

    public static void removeClient(ClientHandler c){
        if(clients.remove(c)){
            clients.forEach(client -> {
                client.sendMessage(c.getName() + " has disconnected!");
            });
        }
    }

    // Running server here
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.startServer();
    }

}
