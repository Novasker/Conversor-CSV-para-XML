package com.conversor.Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

public class Converter {
    public static void writeFile(List<String[]> columns, Path toPath) {
        try {
            File path = new File(String.valueOf(toPath));
            String[][] sheet = new String[columns.size()][8];

            //Transforms the List into a 2d String array
            for(int i = 0; i < columns.size(); i++){
                System.arraycopy(columns.get(i), 0, sheet[i], 0, columns.get(i).length);
            }

            //Calls converting method
            Document doc = writeData(sheet);

            //Calls file writer method
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //Edits some xml output properties
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(path));

            transformer.transform(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private static String defCompany(int sheet){

        //Defines which company it should use
        if(sheet >= 200){
            return "201";
        } else if (sheet < 100){
            return "1";
        } else{
            return "101";
        }
    }

    private static Document writeData(String[][] sheet) throws Exception{

        //Creates root of the document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("ImportacaoLancamentoDTO");
        doc.appendChild(rootElement);

        Element listalancamentos = doc.createElement("ListaLancamentos");
        rootElement.appendChild(listalancamentos);

        Element lancamentoimportacao = doc.createElement("LancamentoImportacao");
        listalancamentos.appendChild(lancamentoimportacao);

        Element nroempresa = doc.createElement("NROEMPRESA");
        nroempresa.setTextContent(defCompany(Integer.parseInt(sheet[1][0])));
        lancamentoimportacao.appendChild(nroempresa);

        Element dtacontabil = doc.createElement("DTACONTABIL");
        dtacontabil.setTextContent(sheet[1][1].replaceAll("/","-"));
        lancamentoimportacao.appendChild(dtacontabil);

        Element nrolote = doc.createElement("NROLOTE");
        nrolote.setTextContent("12");
        lancamentoimportacao.appendChild(nrolote);

        Element extemporaneo = doc.createElement("EXTEMPORANEO");
        extemporaneo.setTextContent("N");
        lancamentoimportacao.appendChild(extemporaneo);

        Element lancamentocontas = doc.createElement("LancamentoContas");
        lancamentoimportacao.appendChild(lancamentocontas);

        //Creates each entry for the file
        for(int i = 1; i< sheet.length; i++){
            Element lancamentoconta = doc.createElement("LancamentoConta");
            lancamentocontas.appendChild(lancamentoconta);

            Element conta = doc.createElement("CONTA");
            conta.setTextContent(sheet[i][2]);
            lancamentoconta.appendChild(conta);

            //Defines if the entry is a Credit or Debit
            Element tipo = doc.createElement("TIPO");
            if(sheet[i][4].contains("C")) {
                tipo.setTextContent("C");
            }   else tipo.setTextContent("D");
            lancamentoconta.appendChild(tipo);

            //Treats the monetary value to be within standards
            Element valor = doc.createElement("VALOR");
            String auxValor = sheet[i][5].replaceAll("\\s", "");
            auxValor = auxValor.replaceAll("\\.", "");
            auxValor = auxValor.replaceAll(",", ".");
            valor.setTextContent(auxValor);
            lancamentoconta.appendChild(valor);

            Element historicocompleto = doc.createElement("HISTORICOCOMPLETO");
            historicocompleto.setTextContent(sheet[i][6]);
            lancamentoconta.appendChild(historicocompleto);

            Element lancamentocontaparams = doc.createElement("LancamentoContaParams");
            lancamentoconta.appendChild(lancamentocontaparams);

            //Creates the main parameter that is used in every entry
            Element lancamentocontaparam = doc.createElement("LancamentoContaParam");
            Element parametro = doc.createElement("PARAMETRO");
            parametro.setTextContent("E");
            lancamentocontaparam.appendChild(parametro);
            Element seqparametrovalor = doc.createElement("SEQPARAMETROVALOR");
            seqparametrovalor.setTextContent(sheet[i][0]);
            lancamentocontaparam.appendChild(seqparametrovalor);
            lancamentocontaparams.appendChild(lancamentocontaparam);

            //Checks to see if a transaction parameter is needed
            if(!sheet[i][3].isEmpty()){
                lancamentocontaparam = doc.createElement("LancamentoContaParam");
                parametro = doc.createElement("PARAMETRO");
                parametro.setTextContent("T");
                lancamentocontaparam.appendChild(parametro);
                seqparametrovalor = doc.createElement("SEQPARAMETROVALOR");
                seqparametrovalor.setTextContent(sheet[i][3]);
                lancamentocontaparam.appendChild(seqparametrovalor);
                lancamentocontaparams.appendChild(lancamentocontaparam);
            }

            //Checks to see if a person or bank is needed
            if (sheet[0][7].contains("P") && !sheet[i][7].isEmpty()){
                lancamentocontaparam = doc.createElement("LancamentoContaParam");
                parametro = doc.createElement("PARAMETRO");
                parametro.setTextContent("PE");
                lancamentocontaparam.appendChild(parametro);
                seqparametrovalor = doc.createElement("SEQPARAMETROVALOR");
                seqparametrovalor.setTextContent(sheet[i][7]);
                lancamentocontaparam.appendChild(seqparametrovalor);
                lancamentocontaparams.appendChild(lancamentocontaparam);
            } else if (sheet[0][7].contains("B") && !sheet[i][7].isEmpty()){
                lancamentocontaparam = doc.createElement("LancamentoContaParam");
                parametro = doc.createElement("PARAMETRO");
                parametro.setTextContent("BC");
                lancamentocontaparam.appendChild(parametro);
                seqparametrovalor = doc.createElement("SEQPARAMETROVALOR");
                seqparametrovalor.setTextContent(sheet[i][7]);
                lancamentocontaparam.appendChild(seqparametrovalor);
                lancamentocontaparams.appendChild(lancamentocontaparam);
            }
        }
        return doc;
    }
}