package com.valiantor.tools;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIUtil{

    public static Boolean isStartwithNumber(String  str ){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        if(!isNum.matches()){
            return false;
        }
        return true;}

    public static void main(String args[]) throws IOException {
            File file;
            file = new File("D:\\question.doc");
            System.out.println(file.exists());
            String str = "";
            try {
                FileInputStream fis = new FileInputStream(file);
                HWPFDocument doc = new HWPFDocument(fis);
                String doc1 = doc.getDocumentText();
                System.out.println(doc1);
                StringBuilder doc2 = doc.getText();
                System.out.println(doc2);
                Range rang = doc.getRange();
                String doc3 = rang.text();
                System.out.println(doc3);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

