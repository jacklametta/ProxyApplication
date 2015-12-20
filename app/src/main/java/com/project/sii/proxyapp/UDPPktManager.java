package com.project.sii.proxyapp;

import java.nio.ByteBuffer;

/**
 * Created by User on 17/12/2015.
 */
public class UDPPktManager extends IPPktManager {

    static final String TAG = "UDPPktManager";
    private ByteBuffer udpPkt;
    static final int SOURCE_PORT_OFFSET = 0;
    static final int DEST_PORT_OFFSET = 2;
    /* UDP Header length */
    int payloadOffset = 0;
}
