package com.mypackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class HtmlFileReader {

    public String readFile(String hFile) {
        boolean eof = false;
        StringBuffer buffer = new StringBuffer();
        String htmlContent = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(hFile));
            while (!eof) {
                String str = br.readLine();
                if (str == null) {
                    eof = true;
                }
                buffer.append(str + "\n");
            }
            htmlContent = buffer.toString();

        } catch (FileNotFoundException fe) {
            System.out.println(fe.toString());
        } catch (IOException io) {
            System.out.println(io.toString());
        }
        return htmlContent;
    }

   /* public static void main(String[] args) throws Exception {
        HtmlFileReader hfr = new HtmlFileReader();
        String htmlFile = "C:\\Users\\Arpit\\IdeaProjects\\mywebserver\\src\\com\\mypackage\\webPage.html";
        String htmlContent = hfr.readFile(htmlFile);
        System.out.println(htmlContent);
    }*/
}
