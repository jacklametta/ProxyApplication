package com.project.sii.proxyapp;

import java.net.DatagramSocket;
import java.util.Hashtable;

public class UDPRecord extends ProtocolRecord {

    Hashtable inOut;
    Hashtable outIn;

    private void insert(int sourceSocketPort, DatagramSocket gateway){
        inOut.put(sourceSocketPort, gateway);
        outIn.put(gateway, sourceSocketPort);
    }

    private void delete(int sourceSocketPort, DatagramSocket gateway){
        inOut.remove(sourceSocketPort);
        outIn.remove(gateway);
    }

    private DatagramSocket getSource(DatagramSocket gateway){
        return (DatagramSocket)(outIn.get(gateway));
    }

    private int getDestination(int source){
        return (Integer)(inOut.get(source));
    }
}
