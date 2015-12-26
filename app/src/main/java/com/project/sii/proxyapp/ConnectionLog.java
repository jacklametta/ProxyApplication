package com.project.sii.proxyapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *  The class is used to create a Log of the overall application.
 */
public class ConnectionLog implements Serializable {

    /*  Instance of @see #CustomClock class  */
    private CustomClock customClock;

    /*  String used to write in the log */
    private String to_log="";

    /*  Class Constructor   */
    public ConnectionLog() {    customClock = new CustomClock();    }

    /**
     * The method opens the txt file used for the log and writes on it
     * @param input it's the string to write in the log
     */
    public void doWrite(String input){
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
