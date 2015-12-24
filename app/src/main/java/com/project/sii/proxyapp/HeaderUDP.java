package com.project.sii.proxyapp;

import java.nio.ByteOrder;

/**
 * Created by User on 24/12/2015.
 */
public class HeaderUDP {
    /*
     *  0                              16                            31
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |           Src Port            |           Dst Port            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     * |            Length             |           Checksum            |
     * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     */

    private static final int SOURCE_PORT_OFFSET = 0;
    public static final int SHORT_SIZE_IN_BYTES = 2;
    private static final int SRC_PORT_SIZE = SHORT_SIZE_IN_BYTES;
    private static final int DEST_PORT_OFFSET
            = SOURCE_PORT_OFFSET + SRC_PORT_SIZE;
    private static final int DEST_PORT_SIZE
            = SHORT_SIZE_IN_BYTES;
    private static final int LENGTH_OFFSET
            = DEST_PORT_OFFSET + DEST_PORT_SIZE;
    private static final int LENGTH_SIZE
            = SHORT_SIZE_IN_BYTES;
    private static final int CHECKSUM_OFFSET
            = LENGTH_OFFSET + LENGTH_SIZE;
    private static final int CHECKSUM_SIZE
            = SHORT_SIZE_IN_BYTES;
    private static final int UDP_HEADER_SIZE
            = CHECKSUM_OFFSET + CHECKSUM_SIZE;
    public static final int BYTE_SIZE_IN_BITS = 8;


    private final int srcPort;
    private final int dstPort;
    private final short length;
    private final short checksum;

    public HeaderUDP(byte[] rawData, int offset, int length){
        this.srcPort
                = (int)(getShort(rawData, SOURCE_PORT_OFFSET + offset, ByteOrder.BIG_ENDIAN));
        this.dstPort
                = (int) getShort(rawData, DEST_PORT_OFFSET + offset, ByteOrder.BIG_ENDIAN);
        this.length = getShort(rawData, LENGTH_OFFSET + offset, ByteOrder.BIG_ENDIAN);
        this.checksum = getShort(rawData, CHECKSUM_OFFSET + offset, ByteOrder.BIG_ENDIAN);
    }

    public int getSrcPort() {
        return this.srcPort;
    }

    public int getDstPort() {
        return this.dstPort;
    }

    public short getLength() {
        return length;
    }

    public short getChecksum() {
        return checksum;
    }

    /*@Override
    protected String buildString() {
        StringBuilder sb = new StringBuilder();
        String ls = System.getProperty("line.separator");

        sb.append("[UDP Header (")
                .append(length())
                .append(" bytes)]")
                .append(ls);
        sb.append("  Source port: ")
                .append(getSrcPort())
                .append(ls);
        sb.append("  Destination port: ")
                .append(getDstPort())
                .append(ls);
        sb.append("  Length: ")
                .append(getLengthAsInt())
                .append(" [bytes]")
                .append(ls);
        sb.append("  Checksum: 0x")
                .append(ByteArrays.toHexString(checksum, ""))
                .append(ls);

        return sb.toString();
    }*/

    public static short getShort(byte[] array, int offset, ByteOrder bo) {
        if (bo.equals(ByteOrder.LITTLE_ENDIAN)) {
            return (short)(
                    ((       array[offset + 1]) << (BYTE_SIZE_IN_BITS * 1))
                            | ((0xFF & array[offset    ])                           )
            );
        }
        else {
            return (short)(
                    ((       array[offset    ]) << (BYTE_SIZE_IN_BITS * 1))
                            | ((0xFF & array[offset + 1])                           )
            );
        }
    }

}
