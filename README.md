# QRCode-Generator-Reader-JAVAFX
## Generate or Read Qr Codes
this JAVAFX desktop app can generate QR-code of URLs or read URL from .png of QR-code

## Features

- Generate QR-code and save it under : /Desktop/[DomainName].png
     - DomainName is the name of website, example :www.github.com -> github
- Read .png and get URL and open it in default browser
    - default browser will be opend based on OS (Linux, Windows, MAC)

## Used Libraries

- [JFoenix](https://mvnrepository.com/artifact/org.rationalityfrontline.workaround/jfoenix/17.0.1) - JavaFX Material Design Library
- [ZXing-core](https://mvnrepository.com/artifact/com.google.zxing/core/3.4.1) - Core barcode encoding/decoding library
- [ZXing-javase](https://mvnrepository.com/artifact/com.google.zxing/javase) - Java SE-specific extensions to core ZXing library


## Build and Run

to build and run project using gradle from terminal

```sh
cd ~/QRCode
gradle build
gradle run
```
###### ***or build and run from intellij idea***

# Screenshots

**Main**

![erro loading file](https://i.ibb.co/tpXX7mC/Mainapp.png)

**Generate QR from given URL**

![erro loading file](https://i.ibb.co/Mpsgv3Q/created.png)

**creation confirmation**

![error loading file](https://i.ibb.co/mHQxNgt/confirm.png)

 **Open .png**
 
![error loading file](https://i.ibb.co/BVM6ZrB/open.png)

## License

GNU GENERAL PUBLIC LICENSE V3

