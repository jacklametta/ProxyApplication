package com.project.sii.proxyapp;

import java.net.DatagramSocket;
import java.util.Hashtable;

public class UDPRecord extends ProtocolRecord {

    private DatagramSocket socket;

    UDPRecord(DatagramSocket socket){
        super();
        this.socket = socket;
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
