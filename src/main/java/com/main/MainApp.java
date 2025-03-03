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
 *  
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        OpenMainScene("/FXMLs/LoginForm.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
