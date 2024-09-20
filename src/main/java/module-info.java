module org.plavsic.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;



    opens org.plavsic.chatapplication.controller to javafx.fxml;
    opens org.plavsic.chatapplication.view to javafx.fxml;

    exports org.plavsic.chatapplication;
}