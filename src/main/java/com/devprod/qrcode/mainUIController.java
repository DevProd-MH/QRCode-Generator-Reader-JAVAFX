package com.devprod.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

public class mainUIController implements Initializable {

    @FXML
    private TextField tf;
    @FXML
    private Hyperlink hl;
    @FXML
    private ImageView img;
    @FXML
    private JFXButton open;
    private String url;

    @FXML
    private void EnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            boolean isURL;
            if (tf.getText().isEmpty()) {
                alert("URL cannot be blank");
                return;
            }
            String regex = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";
            isURL = Pattern.compile(regex).matcher(tf.getText()).find();
            if (!isURL) alert("URL Invalid!");
            else {
                createQR();
            }
        }
    }

    @FXML
    private void openLink() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] browsers = {"google-chrome", "firefox", "mozilla", "epiphany", "konqueror", "netscape", "opera", "links", "lynx"};
        StringBuilder cmd = new StringBuilder();
        for (int i = 0; i < browsers.length; i++)
            if (i == 0) cmd.append(String.format("%s \"%s\"", browsers[i], url));
            else cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
        rt.exec(new String[]{"sh", "-c", cmd.toString()});
    }

    private void createQR() {
        try {
            URL url = new URL(tf.getText());
            String path = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + url.getHost().split("\\.")[1] + ".png";
            BitMatrix matrix = new MultiFormatWriter().encode(new String(tf.getText().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
            alert("QR Code Created");
            setQRImage(path);
            hl.setVisible(false);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    private void readQR(String path) {
        try {
            setQRImage(path);
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            url = result.getText();
            hl.setText("OPEN LINK");
            hl.setVisible(true);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setQRImage(String path) {
        try {
            FileInputStream stream = new FileInputStream(path);
            Image image = new Image(stream);
            img.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        open.setOnAction((final ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open QR Code File...");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separatorChar + "Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) readQR(file.getAbsolutePath());

        });
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}