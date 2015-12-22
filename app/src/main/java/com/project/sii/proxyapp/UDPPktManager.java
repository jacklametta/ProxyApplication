package com.project.sii.proxyapp;

import java.nio.ByteBuffer;

/**
 * Created by User on 17/12/2015.
 */
public class UDPPktManager extends IPPktManager {

    static final String TAG = "UDPPktManager";
    static final int SOURCE_PORT_OFFSET = 0;
    static final int DEST_PORT_OFFSET = 2;
    static final int CHECKSUM_OFFSET = 6;
    static final int LENGTH_OFFSET = 4;

    /* UDP Header length */
    final int UHL = 8;

    public UDPPktManager(){
        super();
    }

    public UDPPktManager(ByteBuffer UDPpkt){
        super();
        this.pkt = UDPpkt;
    }

    private void UDPCreation(){

    }

    private void UDPExtract(){

    }

    public int getDestinationPort(){
        return getBytesFromPkt(IHL + DEST_PORT_OFFSET, 4).getInt();
    }

    public int getSourcePort(){
        return getBytesFromPkt(IHL + SOURCE_PORT_OFFSET, 4).getInt();
    }

    public int getChecksum(){
        return  getBytesFromPkt(IHL + CHECKSUM_OFFSET, 4).getInt();
    }

    public int getLength(){
        return getBytesFromPkt(IHL + LENGTH_OFFSET, 4).getInt();
    }

}
