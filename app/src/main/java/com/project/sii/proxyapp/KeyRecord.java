package com.project.sii.proxyapp;

import java.net.InetAddress;

public class KeyRecord {
    private int appPort;
    private InetAddress serverIp;
    private  int serverPort;

    /* UDP KEY */
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


    public int getAppPort(){ return this.appPort;}
    public InetAddress getServerIp(){ return this.serverIp;}
    public int getServerPort(){ return this.serverPort;}
}
