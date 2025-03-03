/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static Options.Scene.OpenMainScene;

/**
 *
 * @author Mohamed
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the custom font
        Font.loadFont(MainApp.class.getResource("QTSWehda-Bold.otf").toExternalForm(), 10);
        Font.loadFont(MainApp.class.getResource("LamaSans-SemiBold.ttf").toExternalForm(), 10);
        Font.loadFont(MainApp.class.getResource("LamaSans-Light.ttf").toExternalForm(), 10);
//
        OpenMainScene("/FXMLs/LoginForm.fxml");
//        loginedOut();
//
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
//                                "/DateTimeChanged/DeviceTimeChanged.fxml").getController();
//                        c.setMessage(FunReturn);
//                        return;
//                    } else if (FunReturn.equals("المرة القادمة ستنتهي الفترة التجريبية")) {
//                        DeviceTimeChangedController c = Scene.OpenScene_getController(
//                                "/DateTimeChanged/DeviceTimeChanged.fxml").getController();
//                        c.setMessage(FunReturn);
//                        return;
//                    } else if (FunReturn.equals("StopCauseOfDate")) {
//                        Check = 1;
//                    } else {
//                        openDemoTimer = "Open";
//                    }
//                } else {
//                    OpenScene("/Maintainence/Trial/TrialEndDate.fxml");
//                    return;
//                }
//                if (today.isAfter(Expiration_Date)) {
//                    PutTheFinalDate();
//                }
//
//                if (Check == 1) {// means Trial Ended
//                    OpenScene("/Maintainence/Trial/TrialEndDate.fxml");
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
