package lk.ijse.chatApp.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.chatApp.client.Client;
import lk.ijse.chatApp.client.ClientHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientChatFormController implements Initializable{

    private Client client;
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField txtMsg;

    @FXML
    private AnchorPane emojiAnchorPane;

    @FXML
    private GridPane emojiGridpane;

    @FXML
    private Label lblClientName;

    @FXML
    private ImageView chatImage;


    @FXML
    private VBox vBoxClientImage;

    public Client getClient() {

        return client;
    }

    public void setClient(Client client) {

        this.client = client;
    }

    private final String[] emojis = {
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE07", // 😇
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE13"  // 😓
    };

    @FXML
    void btnSendOnAction(ActionEvent event) {
        emojiAnchorPane.setVisible(false);
        String text = txtMsg.getText();
        try {

            if (!Objects.equals(text, "")) {
                appendText(text);
                client.sendMessage(text);
            } else {
                ButtonType ok = new ButtonType("Ok");
                ButtonType cancel = new ButtonType("Cancel");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty message. Is it ok?", ok, cancel);
                alert.showAndWait();
                ButtonType result = alert.getResult();
                if (result.equals(ok)) {
                    client.sendMessage(null);
                }
                txtMsg.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void attachImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; -fx-alignment: center-right;");

                // Display the image in an ImageView or any other UI component
                ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);

                hBox.getChildren().addAll(imageView);
                vbox.getChildren().add(hBox);

                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #5f27cd;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(() -> vbox.getChildren().add(hBox));
    }

    public void setImage(byte[] bytes, String sender) {
        HBox hBox = new HBox();
        Label messageLbl = new Label(sender);
        messageLbl.setStyle("-fx-background-color:   #5f27cd;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");

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

    void appendText(String message) throws IOException {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #6D214F;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vbox.getChildren().add(hBox);
    }

    @FXML
    void loadEmojiOnAction(ActionEvent event) {
        emojiAnchorPane.setVisible(!emojiAnchorPane.isVisible());

    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    private void emojiButtonAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        txtMsg.appendText(button.getText());
    }

    public void setName(String name){
        lblClientName.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(chatImage);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setCycleCount(TranslateTransition.INDEFINITE);
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        /*ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(2), chatImage);
        zoomIn.setFromX(0.8);
        zoomIn.setFromY(0.8);
        zoomIn.setToX(1.2);
        zoomIn.setToY(1.2);
        zoomIn.play();*/

        emojiAnchorPane.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (buttonIndex < emojis.length) {
                    String emoji = emojis[buttonIndex];
                    JFXButton emojiButton = createEmojiButton(emoji);
                    emojiGridpane.add(emojiButton, column, row);
                    buttonIndex++;
                } else {
                    break;
                }
            }
        }
    }

    public void setClientImage(Image image){
        HBox hBox = new HBox();

        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; " );
        // Display the image in an ImageView or any other UI component
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(image);
            imageView.setStyle("-fx-padding: 10px;");
            imageView.setFitHeight(180);
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(imageView);
            vBoxClientImage.getChildren().add(hBox);
        });

       // clientImage=new ImageView(new Image(new ByteArrayInputStream(bytes)));
       // clientImage.setImage(image);
    }

    @FXML
    void txtMsgOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

}
