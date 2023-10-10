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
    public static void convert(Path fromPath, Path toPath) throws Exception {
        try (Reader reader = Files.newBufferedReader(fromPath)) {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            try(CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()){
                List<String[]> allData; allData = csvReader.readAll();
                writer(allData, toPath);
            }
        }
    }
    public static void writer(List<String[]> columns, Path toPath) {
        try {
            Model model = new Model();
            File path = new File(String.valueOf(toPath));
            PrintWriter wr = new PrintWriter(path);
            String[][] table = new String[columns.size()][8];
            for(int i = 0; i < columns.size(); i++){
                System.arraycopy(columns.get(i), 0, table[i], 0, columns.get(i).length);
            }
            String parsedText = model.getHeader();
            String parsedEntry = "";
            if(Integer.parseInt(table[1][0]) >= 200){
                parsedText = parsedText.replaceAll("VAR1", "201");
            } else if (Integer.parseInt(table[1][0]) < 100){
                parsedText = parsedText.replaceAll("VAR1", "1");
            } else{
                parsedText = parsedText.replaceAll("VAR1", "101");
            }
            parsedText = parsedText.replaceAll("VAR2", table[1][1].replaceAll("/","-"));

            for(int i = 1; i< table.length; i++){
                String parsedParam = "";
                if(table[i][4].contains("C")) {
                    String accountEntry = model.getAccountEntry();
                    accountEntry = accountEntry.replaceAll("VAR1", table[i][2]);
                    accountEntry = accountEntry.replaceAll("VAR2", "C");
                    String value = table[i][5].replaceAll("\\s", "");
                    value = value.replaceAll("\\.", "");
                    value = value.replaceAll(",", ".");
                    accountEntry = accountEntry.replaceAll("VAR3", value);
                    accountEntry = accountEntry.replaceAll("VAR4", table[i][6]);
                    accountEntry = accountEntry.replaceAll("VAR5", table[i][0]);
                    if(!table[i][3].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "T");
                        param = param.replaceAll("VAR2", table[i][3]);
                        parsedParam = parsedParam.concat(param + "\n");
                    }
                    if (table[0][7].contains("P") && !table[i][7].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "PE");
                        param = param.replaceAll("VAR2", table[i][7]);
                        parsedParam = parsedParam.concat(param + "\n");
                    } else if (table[0][7].contains("B") && !table[i][7].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "BC");
                        param = param.replaceAll("VAR2", table[i][7]);
                        parsedParam = parsedParam.concat(param + "\n");
                    }
                    accountEntry = accountEntry.replaceAll("VAR6", parsedParam);
                    parsedEntry = parsedEntry.concat(accountEntry + "\n");
                }
            }
            for(int i = 1; i< table.length; i++){
                String parsedParam = "";
                if(table[i][4].contains("D")){
                    String accountEntry = model.getAccountEntry();
                    accountEntry = accountEntry.replaceAll("VAR1", table[i][2]);
                    accountEntry = accountEntry.replaceAll("VAR2", "D");
                    String value = table[i][5].replaceAll("\\s", "");
                    value = value.replaceAll("\\.", "");
                    value = value.replaceAll(",",".");
                    accountEntry= accountEntry.replaceAll("VAR3", value);
                    accountEntry= accountEntry.replaceAll("VAR4", table[i][6]);
                    accountEntry = accountEntry.replaceAll("VAR5", table[i][0]);
                    if(!table[i][3].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "T");
                        param = param.replaceAll("VAR2", table[i][3]);
                        parsedParam = parsedParam.concat(param + "\n");
                    }
                    if (table[0][7].contains("P") && !table[i][7].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "PE");
                        param = param.replaceAll("VAR2", table[i][7]);
                        parsedParam = parsedParam.concat(param + "\n");
                    }else if (table[0][7].contains("B") && !table[i][7].isEmpty()){
                        String param = model.getDynamicParam();
                        param = param.replaceAll("VAR1", "BC");
                        param = param.replaceAll("VAR2", table[i][7]);
                        parsedParam = parsedParam.concat(param + "\n");
                    }
                    accountEntry = accountEntry.replaceAll("VAR6", parsedParam);
                    parsedEntry = parsedEntry.concat(accountEntry + "\n");

                }
            }
            parsedText = parsedText.replaceAll("VAR3", parsedEntry);
            wr.write(parsedText);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}