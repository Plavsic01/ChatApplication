package org.plavsic.chatapplication.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.plavsic.chatapplication.model.Message;
import org.plavsic.chatapplication.model.client.Client;
import org.plavsic.chatapplication.view.ChatView;

import java.util.Optional;

public class ChatController {
    @FXML
    private ListView<Message> chatList;
    @FXML
    private TextField messageField;
    @FXML
    private Button startChatButton;
    @FXML
    private Button sendMessageButton;

    private ChatView chatView;
    private Client client;
    private String username;

    public ChatController() {
        client = new Client();
        client.connectToServer("localhost",3000);
        new Thread(this::readMessage).start();
    }
    @FXML
    private void initializeChatListView(){
        chatList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Message s, boolean b) {
                super.updateItem(s, b);
                if (b || s == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    Label messageLabel = new Label();
                    if (s.isCurrentClient()) {
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        messageLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-border-radius: 5;");
                    } else {
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        messageLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-border-radius: 5;");
                    }
                    messageLabel.setText(s.getMsg());
                    hBox.getChildren().add(messageLabel);
                    setGraphic(hBox);
                }
            }
        });
    }

    @FXML
    private void handleStartChat() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Your Username");
        dialog.setHeaderText("Welcome to Chat Application");
        dialog.setContentText("Please enter your username:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            initializeChatListView();
            setVisibility();
            username = name;
        });
    }

    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
        chatList.setVisible(false);
        sendMessageButton.setVisible(false);
        messageField.setVisible(false);
    }

    public void sendMessage(ActionEvent e){
        if(!messageField.getText().isEmpty()){
            String msg = messageField.getText();
            Message message = new Message(msg,true);
            Platform.runLater(() -> chatList.getItems().add(message));
            client.sendMessage(username + ": " + messageField.getText());
            messageField.clear();
        }
    }

    public void readMessage(){
        String message;
        while((message = client.receiveMessage()) != null){
            final String msg = message;
            Platform.runLater(() -> chatList.getItems().add(new Message(msg,false)));
        }
    }

    public void closeConnection(){
        client.closeConnection();
    }

    private void setVisibility(){
        chatList.setVisible(true);
        messageField.setVisible(true);
        sendMessageButton.setVisible(true);
        startChatButton.setVisible(false);
    }
}
