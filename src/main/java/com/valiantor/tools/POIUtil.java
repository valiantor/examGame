package com.valiantor.tools;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class POIUtil{

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

