package com.project.sii.proxyapp;

import java.net.DatagramSocket;

/**
 * The class is an extension of the @see ProtocolRecord class.
 * It is used to handle the UDP Hashtable's record.
 */
public class UDPRecord extends ProtocolRecord {
    /*  UDP Socket  */
    private DatagramSocket socket;

    /*  Class constructor */
    public UDPRecord(DatagramSocket socket){
        super();
        this.socket = socket;
    }

    /**
     * The function returns the UDP Socket of the object.
     * @return  UDP socket
     */
    public DatagramSocket getSocket(){
        return this.socket;
    }
}
