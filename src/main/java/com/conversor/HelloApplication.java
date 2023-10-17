package com.conversor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(String.valueOf(HelloApplication.class.getResource("Image/icon.png"))));
        stage.setTitle("Conversor XML");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(HelloApplication.class);
    }
}