package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.chatApp.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ClientLoginFormController implements Initializable {

    @FXML
    private AnchorPane clientLoginPane;

    @FXML
    private JFXTextField txtName;

    @FXML
    private ImageView clientImage;

    private Client client;

    private Image image;

    @FXML
    private Label lblNameError;

  //  private byte[] bytes;

    private ClientChatFormController clientChatFormController;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        lblNameError.setVisible(false);
        if (Pattern.matches("^[a-zA-Z\\s]+", txtName.getText())) {

            Client client = new Client(txtName.getText()); //load client
            Thread thread = new Thread(client); //Runnable interface
            thread.start();
            txtName.clear();
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color: blue;-fx-border-radius: 10;");
        }else {
            txtName.setStyle("-fx-border-color: red;-fx-border-radius: 10;");

            lblNameError.setText("* Can't use Numbers as your name or empty name");

            lblNameError.setVisible(true);
            txtName.clear();
            txtName.requestFocus();
        }


    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
            System.exit(0);
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->  txtName.requestFocus());
        lblNameError.setVisible(false);
        txtName.setStyle("-fx-border-color: blue;-fx-border-radius: 10;");
    }
}
