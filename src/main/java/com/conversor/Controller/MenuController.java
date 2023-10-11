package com.conversor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuController {
    String openPath;
    String savePath;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtFieldSim;
    @FXML
    protected void onGerarXMLButtonClick() {
        if(openPath != null){
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Arquivo XML", "*.xml")
                );
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home"),"Desktop"));
                fileChooser.setInitialFileName("Contabil");
                File selectedFile = fileChooser.showSaveDialog(txtFieldSim.getScene().getWindow());
                savePath = selectedFile.getAbsolutePath();
                Path fromPath = Paths.get(openPath);
                Path toPath = Paths.get(savePath);
                Converter.readFile(fromPath, toPath);
                txtFieldSim.setText("");
                JOptionPane.showMessageDialog(null, "Arquivo criado com" +
                        " sucesso!", "Sucesso!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                txtFieldSim.setText("");
            }
        }
    }
    @FXML
    protected void onPesquisarButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"),"Desktop"));
        File selectedFile = fileChooser.showOpenDialog(txtFieldSim.getScene().getWindow());
        openPath = selectedFile.getAbsolutePath();
        txtFieldSim.setText(openPath);
    }

}