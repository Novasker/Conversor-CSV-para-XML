package com.conversor.Controller;

import com.conversor.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ConversorXML");
        stage.getIcons().add(new Image("com/conversor/Image/icon.png"));
        stage.setScene(scene);
        stage.show();
    }
}