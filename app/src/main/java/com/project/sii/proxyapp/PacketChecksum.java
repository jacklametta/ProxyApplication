package com.project.sii.proxyapp;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * The class is used to calculate the checksum of a packet
 */
public class PacketChecksum{
    /*  Packet  */
    ByteBuffer packet;
    /*  Offeset */
    int offset;
    /*  Packet's Length */
    int length;
    /*  CRC32 Checksum  */
    static long crc;

    /*  Class constructor for calculating the checksum on all the packet    */
    public PacketChecksum(ByteBuffer p) {
        this.packet = p;
    }

    /**
     *  Class constructor for calculating the checksum on part of the packet
     *  @param p    packet
     *  @param o    offset
     *  @param l    packet's length
     */
    public PacketChecksum(ByteBuffer p, int o, int l) {
        this.packet = p;
        this.offset = o;
        this.length = l;
        this.crc= 0L;
    }

    /**
     *  The function returns the checksum
     *  @param s    instance of @see PacketChecksum
     *  @return     CRC32 Checksum
     */
    static public long calculateChecksum(PacketChecksum s) {

        Checksum checksum = new CRC32();
        if (null == s.getPacket()) {
            throw new NullPointerException();
        }
        if (s.getOffset() < 0 || s.getLength() < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (s.getPacket().capacity() < (s.getOffset() + s.getLength())) {
            throw new ArrayIndexOutOfBoundsException();
        }

        checksum.update(new byte[s.getPacket().remaining()], s.getOffset(), s.getLength());
        return crc = checksum.getValue();
    }

    /**
     *  The function returns the packet
     *  @return packet
     */
    private ByteBuffer getPacket()  {   return this.packet; }

    /**
     *  The function returns the offset
     *  @return  offeset
     */
    private int getOffset() {   return this.offset; }

    /**
     * The function returns the length
     * @return  length
     */
    private int getLength() {   return this.length; }
}
