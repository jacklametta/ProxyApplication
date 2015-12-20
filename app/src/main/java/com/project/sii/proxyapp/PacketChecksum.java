package com.project.sii.proxyapp;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class PacketChecksum{

    ByteBuffer packet;
    int offset;
    int length;
    static long crc;

    public PacketChecksum(ByteBuffer p, int o, int l) {
        this.packet = p;
        this.offset = o;
        this.length = l;
        this.crc= 0L;
    }

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

    private ByteBuffer getPacket()
    {
        return this.packet;
    }

    private int getOffset()
    {
        return this.offset;
    }

    private int getLength()
    {
        return this.length;
    }
}
