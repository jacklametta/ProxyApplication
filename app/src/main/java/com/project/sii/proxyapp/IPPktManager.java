package com.project.sii.proxyapp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class IPPktManager {
    /** Da considerare ENDIANESS (per pkt dei protocolli di rete BIG_ENDIAN) */
    static final boolean D = true;
    static final String TAG = "pktManager";

    /* Byte Offset in IP header */
    static final int SOURCE_ADDR_OFFSET = 12;
    static final int DEST_ADDR_OFFSET = 16;
    static final int PROTOCOL_OFFSET = 9;
    /* IP Header length */
    int IHL = 20;
    /* Transport Protocol Types */
    static final int UDP = 17;
    static final int TCP = 6;
    static final int ICMP = 1;
    /* IP Packet */
    protected ByteBuffer pkt;
    private int transportProtocol;

    public IPPktManager(){

    }

    public IPPktManager(ByteBuffer pkt) {
        this.pkt = pkt;
        transportProtocol = (int) pkt.get(PROTOCOL_OFFSET);
        IHL = (pkt.get(0) & 0x0f) * 4;
    }

    public int getTransportProtocol(){
        return transportProtocol;
        }

    public ByteBuffer getPayload() {
        return getBytesFromPkt(IHL, pkt.limit() - IHL);
    }


    public InetAddress getSourceAddress() throws UnknownHostException {
        byte[] byteArray = getBytesFromPkt(SOURCE_ADDR_OFFSET, 4).array();
        return InetAddress.getByAddress(byteArray);
    }

    public InetAddress getDestinationAddress() throws UnknownHostException {
        byte[] byteArray = getBytesFromPkt(DEST_ADDR_OFFSET, 4).array();
        return InetAddress.getByAddress(byteArray);
    }

    public IPPktManager setSourceAddress(InetAddress addr){
        byte[] address = addr.getAddress();
        setBytesInPkt(address, SOURCE_ADDR_OFFSET);
        return this;
    }

    public IPPktManager setDestinationAddress(InetAddress addr){
        byte[] address = addr.getAddress();
        setBytesInPkt(address, DEST_ADDR_OFFSET);
        return this;
    }

    public ByteBuffer setBytesInPkt(byte[] src, int offset){

        try {
            pkt.position(offset);
            pkt.put(src);
            pkt.rewind();
        } catch (Exception e){
            e.printStackTrace();
        }
        return pkt;
    }

    public ByteBuffer getBytesFromPkt(int offset, int nCount){

        ByteBuffer ret = null;
        byte[] byteArray = new byte[nCount];

        try{
            pkt.position(offset);
            pkt.get(byteArray, 0, nCount);
            pkt.rewind();
            ret = ByteBuffer.wrap(byteArray);
        } catch (Exception e) {
            //if(D) Log.e(TAG, "getBytesFromPkt: offset " + offset + " pkt.limit " + pkt.limit());
            e.printStackTrace();
        }
        return ret;
    }
}
