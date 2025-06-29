package Controllers;

import Options.MainController;
import Options.Scene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class HomePageController extends MainController implements Initializable {

    private Timeline clockTimeline;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML private Label dateTimeLabel;
    private void startClock() {
        clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            dateTimeLabel.setText(timeFormatter.format(now));
        }));
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ThreadFunc();
    }
    private void ThreadFunc() {
        clock = new Thread() {
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            nowDate = new Date();
                            nowTime = formated.format(nowDate);
                            dateTimeLabel.setText(nowTime);
                        }
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        clock.start();
    }

    private SimpleDateFormat formated = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss aa");

    private String nowTime;

    private Date nowDate;


    private Thread clock;
    @FXML
    void openScanCamera(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/TrackMovement.fxml",575,852 );
    }
    
    @FXML
    void openStationManagement(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/StationsManagement.fxml",575,852 );
    }

    @FXML
    void openUserManagement(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/usersManagement.fxml",575,852 );
    }
    @FXML
    void vehicleRegistrationAction(ActionEvent event) {
        Scene.OpenScene("/FXMLs/VehicleRegistration.fxml");
    }
    
}