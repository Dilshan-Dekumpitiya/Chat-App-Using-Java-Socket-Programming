package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.nio.file.Files;

public class ClientLoginFormController {

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

        clientImage.setImage(null);
        clientImage.setImage(new Image(new FileInputStream("src/main/resources/img/user.png")));


    //   clientChatFormController.setClientImage(bytes);

       // clientChatFormController.setClientImage(image);

//        clientChatFormController.setClientImage(bytes);

    }

    public void setClient(Client client) {

        this.client = client;
    }

    @FXML
    void btnAddClientImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
             //   bytes = Files.readAllBytes(selectedFile.toPath());

                // Display the image in an ImageView or any other UI component
                /*ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);

                hBox.getChildren().addAll(imageView);
                clientImage.setImage(imageView);
                clientImage.getChildren().add(hBox);

                client.sendImage(bytes);*/

                image = new Image(new FileInputStream(selectedFile));
               /* imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);*/

                clientImage.setImage(null);
                clientImage.setImage(image);

              //  getImage(image);




            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Image getImage() {

        return image;
    }
}
