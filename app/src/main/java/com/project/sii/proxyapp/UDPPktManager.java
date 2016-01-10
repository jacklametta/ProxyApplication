package com.project.sii.proxyapp;

import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/*
     *  0                              16                            31
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |           Src Port            |           Dst Port            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |            Length             |           Checksum            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/

/**
 *  The class provides an extension of @see IPPktManager.
 *  It is used to handle with the Layer 3.
 */
public class UDPPktManager extends IPPktManager {
    /*  TAG & Offsets   */
    static final String TAG = "UDPPktManager";
    static final int SOURCE_PORT_OFFSET = 0;
    static final int DEST_PORT_OFFSET = 2;
    static final int CHECKSUM_OFFSET = 6;
    static final int LENGTH_OFFSET = 4;

    /* UDP Header length */
    final int UHL = 8;

    /*  Class Constructor used when the overall packet is provided*/
    public UDPPktManager(ByteBuffer UDPpkt){
        super(UDPpkt);
    }

    /*  Class Constructor when the packet's info are splitted   */
    public UDPPktManager(ByteBuffer payload, InetAddress sAddr, InetAddress dAddr, int sPort, int dPort){
        super();
        this.pkt = ByteBuffer.allocate(IHL + UHL + payload.limit());
        setSourceAddress(sAddr).setDestinationAddress(dAddr);
        setSourcePort(sPort).setDestinationPort(dPort);
        setPayload(payload);
        setChecksum();
        setLength(UHL + payload.limit());
    }

    /*ì
    private void UDPCreation(UDPPktManager pkt, int source, int destination, int length){
        if(source > 0)
            pkt.setSourcePort(source);
        pkt.setDestinationPort(destination);
        pkt.setLength(length);
        pkt.setChecksum();
    }

    private void UDPExtract(){

    }*/

    /**
     * The function returns the destination port
     * @return  destination port
     */
    public int getDestinationPort() {   return getUnsignedShort(getBytesFromPkt(IHL + DEST_PORT_OFFSET, 2).getShort()); }

    /**
     * The function returns the source port
     * @return  source port
     */
    public int getSourcePort()  { return getUnsignedShort(getBytesFromPkt(IHL + SOURCE_PORT_OFFSET, 2).getShort()); }

    /*  CHECKSUM NON ERA LONG ? */   // Sono due bytes, quindi direi di no
    public int getChecksum(){
        return  getUnsignedShort(getBytesFromPkt(IHL + CHECKSUM_OFFSET, 2).getShort());
    }

    /**
     * The function returns the length of the packet
     * @return  packet's length
     */
    public int getLength()  {   return getUnsignedShort(getBytesFromPkt(IHL + LENGTH_OFFSET, 2).getShort());    }

    /**
     * The function sets the int value val in the short field at the specified offset in packet
     * @param val  value to write on the packet
     * @param offset  offset to write on the packet to
     */
    public void setHeaderValue(int val, int offset){
        ByteBuffer b = ByteBuffer.allocate(Short.SIZE); /* The initial order of a byte buffer is always BIG_ENDIAN. */
        b.putShort((short) val);
        byte[] portBytes = b.array();
        setBytesInPkt(portBytes, IHL + offset);
    }

    /**
     * The function sets the destination port of the packet and returns the packet itself.
     * @param port  destination port to write on the packet
     * @return      packet
     */
    public UDPPktManager setDestinationPort(int port) throws  IllegalArgumentException{
        if (port < 0 || port > 65535) throw new IllegalArgumentException(port + " value invalid as port number");
        setHeaderValue(port, DEST_PORT_OFFSET);
        return this;
    }

    /**
     * The function sets the source port of the packet and returns the packet itself.
     * @param port  source port to write on the packet
     * @return      packet
     */
    public UDPPktManager setSourcePort(int port){
        if (port < 0 || port > 65535) throw new IllegalArgumentException(port + " value invalid as port number");
        setHeaderValue(port, SOURCE_PORT_OFFSET);
        return this;
    }

    /**
     * The function sets the packet's checksum
     * @return      packet
     */
    public UDPPktManager setChecksum(){
        ByteBuffer checksum = ByteBuffer.allocate(2); // TODO calcola checksum
        byte[] checkBytes = checksum.array();
        setBytesInPkt(checkBytes, IHL + CHECKSUM_OFFSET);
        return this;
    }

    /**
     * The function sets the length of the packet and returns the packet itself.
     * @param length  length of the packet to write
     * @return        packet
     */
    public UDPPktManager setLength(int length){
        setHeaderValue(length, LENGTH_OFFSET);
        return this;
    }

    /**
     * The function sets the packet's payload and returns the packet itself.
     * @param payload   payload of the packet to write
     * @return          packet
     */
    public UDPPktManager setPayload(ByteBuffer payload) {
        setBytesInPkt(payload.array(), IHL + UHL);
        return this;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("UDPktManager {");
        sb.append("SourcePort: ").append(getSourcePort()).append(", ");
        sb.append("DestPort: ").append(getDestinationPort()).append(", ");
        sb.append("Length: ").append(getLength()).append(", ");
        sb.append("Checksum: ").append(getChecksum()).append("}");
        return sb.toString();
    }

}
