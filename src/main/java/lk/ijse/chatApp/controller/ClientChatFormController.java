package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.chatApp.client.Client;

import java.io.ByteArrayInputStream;

public class ClientChatFormController {

    private Client client;
    @FXML
    private VBox vbox;

    public Client getClient() {

        return client;
    }

    public void setClient(Client client) {

        this.client = client;
    }
    public void writeMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(() -> vbox.getChildren().add(hBox));
    }

    public void setImage(byte[] bytes, String sender) {
        HBox hBox = new HBox();
        Label messageLbl = new Label(sender);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");

        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; " + (sender.equals(client.getName()) ? "-fx-alignment: center-right;" : "-fx-alignment: center-left;"));
        // Display the image in an ImageView or any other UI component
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setStyle("-fx-padding: 10px;");
            imageView.setFitHeight(180);
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(messageLbl, imageView);
            vbox.getChildren().add(hBox);
        });
    }
}
