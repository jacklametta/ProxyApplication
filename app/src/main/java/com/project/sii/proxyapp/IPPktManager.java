package com.project.sii.proxyapp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Created by User on 17/12/2015.
 */
public class IPPktManager {

    static final boolean D = true;
    static final String TAG = "pktManager";

    /* Byte Offset in IP header */
    static final int SOURCE_ADDR_OFFSET = 12;
    static final int DEST_ADDR_OFFSET = 16;
    static final int PROTOCOL_OFFSET = 9;
    /* IP Header length */
    int payloadOffset = 0;
    /* Transport Protocol Types */
    static final int UDP = 17;
    static final int TCP = 6;
    static final int ICMP = 1;
    /* IP Packet */
    private ByteBuffer pkt;
    private int transportProtocol;

    public IPPktManager() {
        /* Inserire i campi che sono comuni con gli altri manager*/
    }

    public IPPktManager(ByteBuffer pkt) {
        this.pkt = pkt;
        transportProtocol = (int) pkt.get(PROTOCOL_OFFSET);
        payloadOffset = ((int)(pkt.get(0) & 0x0f) * 4);
    }

    public int getTransportProtocol(){
        return transportProtocol;
    }

    public ByteBuffer getPayload() throws UnknownHostException {
        return getBytesFromPkt(payloadOffset, pkt.limit() - payloadOffset );
    }

    public ByteBuffer getTransportPayload(){    // TO-DO
        return pkt;
    }

    public InetAddress getSourceAddress() throws UnknownHostException {
        byte[] byteArray = getBytesFromPkt(SOURCE_ADDR_OFFSET, 4).array();
        return InetAddress.getByAddress(byteArray);
    }

    public InetAddress getDestinationAddress() throws UnknownHostException {
        byte[] byteArray = getBytesFromPkt(DEST_ADDR_OFFSET, 4).array();
        return InetAddress.getByAddress(byteArray);
    }

    public ByteBuffer getBytesFromPkt(int offset, int nCount){

        ByteBuffer ret = null;
        byte[] byteArray = new byte[nCount];

        try{
            pkt.position(offset);
            pkt.get(byteArray, 0, nCount);
            pkt.rewind();
            ret.wrap(byteArray);
        } catch (IllegalArgumentException e){
            //if(D) Log.e(TAG, "getBytesFromPkt: offset " + offset + " pkt.limit " + pkt.limit());
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
