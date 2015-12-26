package com.project.sii.proxyapp;

/**
 * Created by User on 24/12/2015.
 */
public interface Packet {/*
    /**
     * Returns the Header object representing this packet's header.
     *
     * @return the Header object representing this packet's header.
     *         May be null if the header doesn't exist
     */
    public Header getHeader();

    /**
     * Returns the Packet object representing this packet's payload.
     *
     * @return the Packet object representing this packet's payload.
     *         May be null if the payload exist
     */
    public Packet getPayload();

    /**
     * Returns the packet length in bytes.
     *
     * @return the length of the byte stream of the packet
     *         represented by this object in bytes
     */
    public int length();

    /**
     * Returns this packet's raw data.
     *
     * @return this packet's raw data, namely the byte stream
     *         which is actually sent through real network
     */
    public byte[] getRawData();

    /**
     * Traverses this packet and its payload to find an object of
     * the specified packet class and returns the object.
     * If there are more than one objects of the specified class in this object,
     * this method returns the most outer one of them.
     *
     * @param <T> packet
     * @param clazz the packet class of the object to get
     * @return a packet object if found; otherwise null
     */
}