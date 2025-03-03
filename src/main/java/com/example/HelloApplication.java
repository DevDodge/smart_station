package com.example;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Options.Scene.OpenMainScene;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the custom font
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/QTSWehda-Bold.otf")).toExternalForm(), 10);
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/LamaSans-SemiBold.ttf")).toExternalForm(), 10);
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/LamaSans-Light.ttf")).toExternalForm(), 10);

        OpenMainScene("/FXMLs/LoginForm.fxml");

//        boolean ifHDtrue = true;
//        if (ifHDtrue) {
////            Reception.loginedOut();
//            String openDemoTimer = "";
//            String[] DateAfterDecode = {""};
//            String DateBeforeDecode;
//            DateBeforeDecode = getTheExpiredDate();
//            DateAfterDecode = DecodeFUN(DateBeforeDecode);
//            int ExpiredYear = Integer.parseInt(DateAfterDecode[0]);
//            int ExpiredMonth = Integer.parseInt(DateAfterDecode[1]);
//            int ExpiredDay = Integer.parseInt(DateAfterDecode[2]);
//            LocalDate today = LocalDate.now();
//            LocalDate Expiration_Date = LocalDate.of(ExpiredYear, ExpiredMonth, ExpiredDay);
//            int Check = checkTheFinalDate();
//            int Check101 = checkifDemoStopped();// 101 if there is no trial , 0 if trial is active, 1 if trial ended
//            if (Check101 == 101) {// if no trial means active all the time
//                OpenScene("/FXMLs/LoginForm.fxml");
//            } else {
//                int checkDemo = checkifDemoStopped();
//                if (checkDemo == 0) {// not stopped means active trial
//                    String FunReturn = Control_PC_DateTime.Check_Computer_Date_OnOpen();
//                    if (FunReturn.equals("مرتين")) {
//                        DeviceTimeChangedController c = Scene.OpenScene_getController(
//                                "/FXMLs/Maintainence/DeviceTimeChanged.fxml").getController();
//                        c.setMessage(FunReturn);
//                        return;
//                    } else if (FunReturn.equals("المرة القادمة ستنتهي الفترة التجريبية")) {
//                        DeviceTimeChangedController c = Scene.OpenScene_getController(
//                                "/FXMLs/Maintainence/DeviceTimeChanged.fxml").getController();
//                        c.setMessage(FunReturn);
//                        return;
//                    } else if (FunReturn.equals("StopCauseOfDate")) {
//                        Check = 1;
//                    } else {
//                        openDemoTimer = "Open";
//                    }
//                } else {
//                    OpenScene("/FXMLs/Maintainence/TrialEndDate.fxml");
//                    return;
//                }
//                if (today.isAfter(Expiration_Date)) {
//                    PutTheFinalDate();
//                }
//
//                if (Check == 1) {// means Trial Ended
//                    OpenScene("/FXMLs/Maintainence/TrialEndDate.fxml");
//                } else {
//                    OpenScene("/FXMLs/LoginForm.fxml");
//                    if (openDemoTimer.equals("Open")) {
//                        OpenScene("/FXMLs/Maintainence/DemoTimer.fxml");
//                    }
//                }
//            }
//
//        } else {
//            OpenScene("/DevicePremesion/GetPremession.fxml");
//        }
    }

    public static void main(String[] args) {
        launch();
    }
}