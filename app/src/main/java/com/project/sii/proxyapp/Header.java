package com.project.sii.proxyapp;

import java.io.Serializable;

/**
 * Created by User on 24/12/2015.
 */
public interface Header extends Serializable {

        /**
         * Returns the header length in bytes.
         *
         * @return the length of the byte stream of the header
         *         represented by this object in bytes
         */
        public long getLength();

        /**
         * Returns the raw data of this packet's header.
         *
         * @return the raw data of this packet's header,
         *         namely a piece of the byte stream
         *         which is actually sent through real network
         */
        public byte[] getRawData();

}
