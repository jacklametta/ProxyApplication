package com.project.sii.proxyapp;

/**
 * Created by User on 24/12/2015.
 */
public class UDPPacket implements Packet {

    /*private final UDPHeader header;
    private final Packet payload;
*/
    @Override
    public Header getHeader() {
        return null;
    }

    @Override
    public Packet getPayload() {
        return null;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getRawData() {
        return new byte[0];
    }
/*
    public static UDPPacket newPacket(
            byte[] rawData, int offset, int length
    ) throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new UdpPacket(rawData, offset, length);
    }

    private UDPPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new UdpHeader(rawData, offset, length);

        int payloadLength = header.getLengthAsInt() - header.length();
        if (payloadLength < 0) {
            throw new IllegalRawDataException(
                    "The value of length field seems to be wrong: "
                            + header.getLengthAsInt()
            );
        }

        if (payloadLength > length - header.length()) {
            payloadLength = length - header.length();
        }

        if (payloadLength != 0) { // payloadLength is positive.
            this.payload
                    = PacketFactories.getFactory(Packet.class, UdpPort.class)
                    .newInstance(rawData, offset + header.length(), payloadLength, header.getDstPort());
        }
        else {
            this.payload = null;
        }
    }

    private UdpPacket(Builder builder) {
        if (
                builder == null
                        || builder.srcPort == null
                        || builder.dstPort == null
                ) {
            StringBuilder sb = new StringBuilder();
            sb.append("builder: ").append(builder)
                    .append(" builder.srcPort: ").append(builder.srcPort)
                    .append(" builder.dstPort: ").append(builder.dstPort);
            throw new NullPointerException(sb.toString());
        }

        if (builder.correctChecksumAtBuild) {
            if (builder.srcAddr == null || builder.dstAddr == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("builder.srcAddr: ").append(builder.srcAddr)
                        .append(" builder.dstAddr: ").append(builder.dstAddr);
                throw new NullPointerException(sb.toString());
            }
            if (!builder.srcAddr.getClass().isInstance(builder.dstAddr)) {
                StringBuilder sb = new StringBuilder();
                sb.append("builder.srcAddr: ").append(builder.srcAddr)
                        .append(" builder.dstAddr: ").append(builder.dstAddr);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
        this.header = new UdpHeader(
                builder,
                payload != null ? payload.getRawData() : new byte[0]
        );
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getRawData() {
        return new byte[0];
    }

    /**
     *
     * checksum varification is necessary for IPv6(i.e. acceptZero must be false)
     *
     * @param srcAddr srcAddr
     * @param dstAddr dstAddr
     * @param acceptZero acceptZero
     * @return true if the packet represented by this object has a valid checksum;
     *         false otherwise.
     *//*
    public boolean hasValidChecksum(
            InetAddress srcAddr, InetAddress dstAddr, boolean acceptZero
    ) {
        if (srcAddr == null || dstAddr == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("srcAddr: ").append(srcAddr)
                    .append(" dstAddr: ").append(dstAddr);
            throw new NullPointerException(sb.toString());
        }
        if (!srcAddr.getClass().isInstance(dstAddr)) {
            StringBuilder sb = new StringBuilder();
            sb.append("srcAddr: ").append(srcAddr)
                    .append(" dstAddr: ").append(dstAddr);
            throw new IllegalArgumentException(sb.toString());
        }

        if (header.checksum == 0) {
            if (acceptZero) { return true; }
            else { return false; }
        }

        if (payload != null) {
            return header.calcChecksum(srcAddr, dstAddr, payload.getRawData())
                    == header.checksum;
        }
        else {
            return header.calcChecksum(srcAddr, dstAddr, new byte[0])
                    == header.checksum;
        }
    }

    @Override
    public Builder getBuilder() {
        return new Builder(this);
    }
*/
}
