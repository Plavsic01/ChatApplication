package org.plavsic.chatapplication;

import javafx.application.Application;
import javafx.stage.Stage;
import org.plavsic.chatapplication.view.ChatView;

public class ChatApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChatView chatView = new ChatView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
