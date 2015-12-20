package com.project.sii.proxyapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by User on 20/12/2015.
 */
public class ConnectionLog implements Serializable {

    private Orologio orologio;
    String to_log="";

    public ConnectionLog() {

        orologio = new Orologio();
    }

     public void registra(String input){
        String path = "Ser/log.txt";
        FileWriter fw;
        try {
            File file = new File(path);
            fw = new FileWriter(file,true);
            fw.write(input+"\r\n");
            fw.flush();
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
