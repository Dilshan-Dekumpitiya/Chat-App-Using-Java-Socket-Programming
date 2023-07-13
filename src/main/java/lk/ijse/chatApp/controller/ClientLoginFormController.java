package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

public class ClientLoginFormController implements Initializable {

    @FXML
    private AnchorPane clientLoginPane;

    @FXML
    private JFXTextField txtName;

    @FXML
    private ImageView clientImage;

    private Client client;

    private Image image;

  //  private byte[] bytes;

    private ClientChatFormController clientChatFormController;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Client client = new Client(txtName.getText()); //load client
        Thread thread = new Thread(client); //Runnable interface
        thread.start();
        txtName.clear();

      //  clientImage.setImage(null);
      //  clientImage.setImage(new Image(new FileInputStream("src/main/resources/img/user.png")));

    }

    public void setClient(Client client) {

        this.client = client;
    }



    public Image getImage() {

        return image;
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->  txtName.requestFocus());
    }
}
