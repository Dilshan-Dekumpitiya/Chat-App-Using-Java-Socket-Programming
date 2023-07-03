package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chatApp.client.Client;

import java.io.IOException;

public class ClientLoginFormController {

    @FXML
    private AnchorPane clientLoginPane;
    @FXML
    private JFXTextField txtName;
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Client client = new Client(txtName.getText());
        Thread thread = new Thread(client);
        thread.start();
        Stage stage = (Stage) clientLoginPane.getScene().getWindow();
     //   stage.close();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("./view/ClientChatForm.fxml"))));
      //  stage.show();
    }

}
