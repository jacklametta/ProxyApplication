package com.project.sii.proxyapp;
import java.net.DatagramSocket;
import java.util.Hashtable;

/**
 * Created by User on 17/12/2015.
 */
public class ProtocolRecord {

    Hashtable inOut;
    Hashtable outIn;

    public ProtocolRecord(){
        if (inOut.isEmpty())
            inOut = new Hashtable();
        if (outIn.isEmpty())
            outIn = new Hashtable();
    }

    private void insert(){

    }

    private void delete(){

    }

    private void search(){

    }

    private Hashtable getInOut(){
        return inOut;
    }

    private Hashtable getOutIn(){
        return outIn;
    }
}
