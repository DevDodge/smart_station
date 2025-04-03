package com.example;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static Options.Scene.OpenMainScene;


public class HelloApplication extends Application {
    
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws IOException {
       // Load the custom font
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/QTSWehda-Bold.otf")).toExternalForm(), 10);
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/LamaSans-SemiBold.ttf")).toExternalForm(), 10);
        Font.loadFont(Objects.requireNonNull(HelloApplication.class.getResource("/Fonts/LamaSans-Light.ttf")).toExternalForm(), 10);
        OpenMainScene("/FXMLs/LoginForm.fxml");
    }
}