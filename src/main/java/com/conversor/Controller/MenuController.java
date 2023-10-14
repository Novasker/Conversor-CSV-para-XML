package com.conversor.Controller;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.conversor.Controller.Converter.writeFile;

public class MenuController {
    String openPath;
    String savePath;
    @FXML
    private TextField txtFieldSim;
    @FXML
    protected void onPesquisarButtonClick() {
        //Select original file path
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"),"Desktop"));
        File selectedFile = fileChooser.showOpenDialog(txtFieldSim.getScene().getWindow());
        openPath = selectedFile.getAbsolutePath();
        txtFieldSim.setText(openPath);
    }
    @FXML
    protected void onGerarXMLButtonClick() {
        if(openPath != null){
            try {
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
                //Select desired save file path
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Arquivo XML", "*.xml")
                );
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home"),"Desktop"));
                fileChooser.setInitialFileName("CTB_"+timeStamp);
                File selectedFile = fileChooser.showSaveDialog(txtFieldSim.getScene().getWindow());
                savePath = selectedFile.getAbsolutePath();
                //Original file path
                Path fromPath = Paths.get(openPath);
                //Save location file path
                Path toPath = Paths.get(savePath);
                //Calls file interpreting method
                readFile(fromPath, toPath);
                txtFieldSim.setText("");
                JOptionPane.showMessageDialog(null, "Arquivo criado com" +
                        " sucesso!", "Sucesso!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao "+
                        "selecionar o caminho.", "Erro!", JOptionPane.ERROR_MESSAGE);
                txtFieldSim.setText("");
            }
        } else{
            JOptionPane.showMessageDialog(null, "Erro ao "+
                    "selecionar o caminho.", "Erro!", JOptionPane.ERROR_MESSAGE);
            txtFieldSim.setText("");
        }
    }

    public static void readFile(Path fromPath, Path toPath) throws Exception {
        //Reads all data and saves each value in a list of String array
        try (Reader reader = Files.newBufferedReader(fromPath)) {
            //Parses ; separated CSV
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            try(CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()){
                //Transforms the sheet into a List
                List<String[]> allData; allData = csvReader.readAll();
                writeFile(allData, toPath);
            }
        }
    }

}