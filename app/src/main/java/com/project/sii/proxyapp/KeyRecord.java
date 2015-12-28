package com.project.sii.proxyapp;

import java.net.InetAddress;

/**
 *  The class provides 3 different constructor, one for each transport protocol, for handling
 *  the keys for the hashtables' records.
 */
public class KeyRecord {
    /*  Application Port */
    private int appPort;
    /*  Server IP Address   */
    private InetAddress serverIp;
    /*  Server Port */
    private  int serverPort;

    /**
     * UDP's key class constructor
     * @param app       application port
     * @param ip        server IP address
     * @param server    server port
     */
    public KeyRecord(int app, InetAddress ip, int server)
    {
        this.appPort = app;
        this.serverIp = ip;
        this.serverPort = server;
    }

    /* TCP & ICMP KEY */
    public KeyRecord()
    {
        // TODO TCP e ICMP keyRecords
    }

    /**
     *  The function returns the Application port
     *  @return application port
     */
    public int getAppPort(){ return this.appPort;}

    /**
     *  The function returns the Server IP address
     *  @return Server IP address
     */
    public InetAddress getServerIp(){ return this.serverIp;}

    /**
     *  The function returns the Server port
     *  @return server port
     */
    public int getServerPort(){ return this.serverPort;}
}
