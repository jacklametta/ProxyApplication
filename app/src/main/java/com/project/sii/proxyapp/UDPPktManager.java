package com.project.sii.proxyapp;

import java.nio.ByteBuffer;

/*
     *  0                              16                            31
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |           Src Port            |           Dst Port            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |            Length             |           Checksum            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
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

    public UDPPktManager setDestinationPort(int port){
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE); /* The initial order of a byte buffer is always BIG_ENDIAN. */
        b.putInt(port);
        byte[] portBytes = b.array();
        setBytesInPkt(portBytes, IHL + DEST_PORT_OFFSET);
        return this;
    }

    public UDPPktManager setSourcePort(int port){
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE); /* The initial order of a byte buffer is always BIG_ENDIAN. */
        b.putInt(port);
        byte[] portBytes = b.array();
        setBytesInPkt(portBytes, IHL + SOURCE_PORT_OFFSET);
        return this;
    }

    public UDPPktManager setChecksum(){
        ByteBuffer checksum = ByteBuffer.allocate(2);
        byte[] checkBytes = checksum.array();
        setBytesInPkt(checkBytes, IHL + CHECKSUM_OFFSET);
        return this;
    }

    public UDPPktManager setLength(int length){
        ByteBuffer b = ByteBuffer.allocate(Integer.SIZE); /* The initial order of a byte buffer is always BIG_ENDIAN. */
        b.putInt(length);
        byte[] lengthBytes = b.array();
        setBytesInPkt(lengthBytes, IHL + LENGTH_OFFSET);
        return this;
    }

}
