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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.chatApp.client.Client;

import java.io.*;
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
    private AnchorPane unicodeEmojiAnchorPane;

    @FXML
    private ImageView chatImage;

    private File filePath;


    @FXML
    private VBox vBoxClientImage;


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
        unicodeEmojiAnchorPane.setVisible(false);
        String text = txtMsg.getText();
        try {

            if (!Objects.equals(text, "")) {
                appendText(text);
                client.sendMessage(text);
                txtMsg.clear();

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
            //    imageView.setFitHeight(180);
            //      imageView.setFitWidth(100);

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
         //   imageView.setFitHeight(180);
         //    imageView.setFitWidth(100);

         //     imageView.setFitHeight(80);
        //    imageView.setFitWidth(80);

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
    void loadUnicodeEmojiOnAction(ActionEvent event) {
        unicodeEmojiAnchorPane.setVisible(!unicodeEmojiAnchorPane.isVisible());
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

        emojiAnchorPane.setVisible(false);
        unicodeEmojiAnchorPane.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
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

    @FXML
    void txtMsgOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    @FXML
    void loadEmojiOnAction(ActionEvent event) {
        emojiAnchorPane.setVisible(!emojiAnchorPane.isVisible());
    }

    @FXML
    void tearsOfJoyEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/tears-of-joy.png");

    }

    @FXML
    void floorLaughingEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/floor-laughing.png");
    }

    @FXML
    void faceKissEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-kiss.png");
    }

    @FXML
    void grinningFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/grinning-face.png");
    }

    @FXML
    void faceWithHeartEyesEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-heart-eyes.png");
    }

    @FXML
    void faceWithHeartsEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-hearts.png");
    }

    @FXML
    void cryingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/crying-face.png");
    }

    @FXML
    void droolingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/drooling-face.png");
    }

    @FXML
    void faceFoodEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-food.png");
    }

    @FXML
    void faceWithHandEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-hand.png");
    }

    @FXML
    void faceWthSunglassesEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-sunglasses.png");
    }

    @FXML
    void faceWithSpiralEyesEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-spiral-eyes.png");
    }

    @FXML
    void faceWithTearEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-tear.png");
    }

    @FXML
    void faceWthTongueEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-with-tongue.png");
    }

    @FXML
    void fearfulFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/fearful-face.png");
    }

    @FXML
    void relievedFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/relieved-face.png");
    }

    @FXML
    void shushingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/shushing-face.png");
    }

    @FXML
    void smilingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/smiling-face.png");
    }

    @FXML
    void smilingFaceWithHaloEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/smiling-face-with-halo.png");
    }

    @FXML
    void thinkingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/thinking-face.png");
    }

    @FXML
    void zanyFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/zany-face.png");
    }

    @FXML
    void zipperMouthFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/zipper-mouth-face.png");
    }

    @FXML
    void faceWthoutMouthEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-without-mouth.png");
    }

    @FXML
    void faceScreamingEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/face-screaming.png");
    }

    @FXML
    void eyesEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/eyes.png");
    }

    @FXML
    void partyingFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/partying-face.png");
    }

    @FXML
    void hearMonkeyEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/hear-monkey.png");
    }

    @FXML
    void seeMonkeyEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/see-monkey.png");
    }

    @FXML
    void moonFaceEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/moon-face.png");
    }

    @FXML
    void heartEmojiMouseClick(MouseEvent event) throws IOException {
        emojiSet("src/main/resources/emojis/heart.png");
    }

    private void emojiSet(String path) throws IOException {

        this.filePath = new File(path);
        byte[] bytes = Files.readAllBytes(filePath.toPath());

        HBox hBox = new HBox();
        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 50; -fx-max-width: 50; -fx-padding: 10; -fx-alignment: center-right;");

        hBox.setStyle("-fx-alignment: center-right;");

        // Display the image in an ImageView or any other UI component
        ImageView imageView = new ImageView(new Image(new FileInputStream(filePath)));
        imageView.setStyle("-fx-padding: 10px;");
      //  imageView.setFitHeight(50);
     //   imageView.setFitWidth(50);

        hBox.getChildren().addAll(imageView);
        vbox.getChildren().add(hBox);

        client.sendImage(bytes);
        emojiAnchorPane.setVisible(false);
    }

}
