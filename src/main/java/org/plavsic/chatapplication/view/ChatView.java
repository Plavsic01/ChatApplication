package org.plavsic.chatapplication.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.plavsic.chatapplication.ChatApplication;
import org.plavsic.chatapplication.controller.ChatController;

import java.io.IOException;

public class ChatView {

    public ChatView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("Main.fxml"));
        Parent root = fxmlLoader.load();
        ChatController controller = fxmlLoader.getController();
        controller.setChatView(this);
        Scene scene = new Scene(root);
        stage.setTitle("Chat Application");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            controller.closeConnection();
            System.out.println("Connection closed");
        });
    }



}
