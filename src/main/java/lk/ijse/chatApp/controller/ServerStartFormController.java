package lk.ijse.chatApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chatApp.server.Server;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class ServerStartFormController {


    public AnchorPane startServerPane;
    @FXML
    void btnServerStartOnAction(ActionEvent event) throws IOException {

        startServer();

        Stage stage = (Stage) startServerPane.getScene().getWindow();

        stage.close();

        Stage stage2 = new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientLoginForm.fxml"))));
        stage2.setTitle("Live Chat Login");
        stage2.show();

        Notifications notification = NotificationController.notification("Server up & running", "Server Alert");
        notification.show();
    }
    private void startServer() throws IOException {
        Runnable server = Server.getServerSocket();
        Thread thread = new Thread(server);
        thread.start();
    }
}
