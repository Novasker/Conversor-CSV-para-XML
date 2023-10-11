package com.conversor.Controller;

import com.conversor.Model.Model;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Converter {
    public static void readFile(Path fromPath, Path toPath) throws Exception {
        //Reads all data and saves each value in a list of String array
        try (Reader reader = Files.newBufferedReader(fromPath)) {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            try(CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()){
                List<String[]> allData; allData = csvReader.readAll();
                writeFile(allData, toPath);
            }
        }
    }
    public static void writeFile(List<String[]> columns, Path toPath) {
        try {
            Model model = new Model();
            File path = new File(String.valueOf(toPath));
            PrintWriter wr = new PrintWriter(path);
            String[][] sheet = new String[columns.size()][8];
            //Defines max column amount
            for(int i = 0; i < columns.size(); i++){
                System.arraycopy(columns.get(i), 0, sheet[i], 0, columns.get(i).length);
            }
            String parsedText = defCompany(model.getHeader(), Integer.parseInt(sheet[1][0]));
            //Defines the company to be used
            String parsedEntry = writeData(sheet, model);
            parsedText = parsedText.replaceAll("VAR2", sheet[1][1].replaceAll("/","-"));
            parsedText = parsedText.replaceAll("VAR3", parsedEntry);
            wr.write(parsedText);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static String defCompany(String header, int sheet){
        if(sheet >= 200){
            header = header.replaceAll("VAR1", "201");
        } else if (sheet < 100){
            header = header.replaceAll("VAR1", "1");
        } else{
            header = header.replaceAll("VAR1", "101");
        }
        return header;
    }
    private static String writeData(String[][] sheet, Model model){
        String parsedEntry = "";
        for(int i = 1; i< sheet.length; i++){
            String parsedParam = "";
            String accountEntry = model.getAccountEntry();
            accountEntry = accountEntry.replaceAll("VAR1", sheet[i][2]);
            if(sheet[i][4].contains("C")) {
                accountEntry = accountEntry.replaceAll("VAR2", "C");
            }   else accountEntry = accountEntry.replaceAll("VAR2", "D");

            String value = sheet[i][5].replaceAll("\\s", "");
            value = value.replaceAll("\\.", "");
            value = value.replaceAll(",", ".");
            accountEntry = accountEntry.replaceAll("VAR3", value);
            accountEntry = accountEntry.replaceAll("VAR4", sheet[i][6]);
            accountEntry = accountEntry.replaceAll("VAR5", sheet[i][0]);
            String params = defParam(sheet, model.getDynamicParam(), i);
            parsedParam = parsedParam.concat(params + "\n");
            accountEntry = accountEntry.replaceAll("VAR6", parsedParam);
            parsedEntry = parsedEntry.concat(accountEntry + "\n");
        }
        return parsedEntry;
    }

    private static String defParam(String[][] sheet, String model, int i) {
        //Creates the dynamic parameters
        if(!sheet[i][3].isEmpty()){
            model = model.replaceAll("VAR1", "T");
            model = model.replaceAll("VAR2", sheet[i][3]);
        } else model = "";
        if (sheet[0][7].contains("P") && !sheet[i][7].isEmpty()){
            model = model.replaceAll("VAR1", "PE");
            model = model.replaceAll("VAR2", sheet[i][7]);
        } else if (sheet[0][7].contains("B") && !sheet[i][7].isEmpty()){
            model = model.replaceAll("VAR1", "BC");
            model = model.replaceAll("VAR2", sheet[i][7]);
        }
        return model;
    }

}