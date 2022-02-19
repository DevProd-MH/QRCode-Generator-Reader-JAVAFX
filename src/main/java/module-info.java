module com.devprod.qrcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires java.desktop;
    requires com.jfoenix;


    opens com.devprod.qrcode to javafx.fxml;
    exports com.devprod.qrcode;
}