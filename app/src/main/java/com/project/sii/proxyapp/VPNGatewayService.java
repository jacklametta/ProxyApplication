package com.project.sii.proxyapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import java.net.DatagramSocket;
import java.util.Hashtable;

/**
 * The class provides a background service for several purpose:
 *  -   Creation and Update of the log;
 *  -   Hashtables management;
 *  -   Multithread management;
 */
public class VPNGatewayService extends Service {

    private static String TAG = "VPNGatewayService";
    /*  One hashtable for every managed transport protocol  */
    Hashtable udpTable, tcpTable, icmpTable;

    /**
     * TO DO
     * @param intent    Received Intent from MainActivity
     * @return          Nothing
     */
    @Override
     public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // It is used only when the service is instantiated
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "VPNGatewayService created");
        udpTable = new Hashtable();
        tcpTable = new Hashtable();
        icmpTable = new Hashtable();
    }

    /**
     * The function is called when @see MainActivity starts the service who has been already
     * created
     * @param intent    Received Intent from MainActivity
     * @param flags     arguments
     * @param startId   token representing the MainAcitivty's start request
     * @return          flag explicits if the service's killed before finishing, don't recreate it
     */
     @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Log.d(TAG, "VPNGatewayService resumed");
        return Service.START_NOT_STICKY;
    }

    /**
     * The method is dalled by the system to notify Service is no longer used and is being removed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "VPNGatewayService destroyed");
    }

    /**
     * The method inserts an UDP record in the hashtable
     * @param key       key record
     * @param gateway   socket
     * @param ttl       time to live
     */
    private void udpInsert(KeyRecord key, DatagramSocket gateway, long ttl) {
        Pair<DatagramSocket,Long> udpPair;
        udpTable.put(key, new Pair<DatagramSocket, Long>(gateway, ttl));
    }

    /**
     * The method removes the UDP record based on the value of the key
     * @param key   Key of the record
     */
    private void udpDelete(KeyRecord key){
        udpTable.remove(key);
    }

    /**
     * The method removes the TCP record based on the value of the key
     * @param key   Key of the record
     */
    private void tcpDelete(KeyRecord key){
        tcpTable.remove(key);
    }

    /**
     * The method removes the ICMP record based on the value of the key
     * @param key   Key of the record
     */
    private void icmpDelete(KeyRecord key){
        icmpTable.remove(key);
    }

    /**
     * The function returns the table who contains UDP Records
     * @return  UDP Records Hashtable
     */
    private Hashtable getUdpTable(){
        return udpTable;
    }

    /**
     * The function returns the table who contains TCP Records
     * @return  TCP Records Hashtable
     */
    private Hashtable getTcpTable(){
        return tcpTable;
    }

    /**
     * The function returns the table who contains ICMP Records
     * @return  ICMP Records Hashtable
     */
    private Hashtable getIcmpTable(){
        return icmpTable;
    }

    /**
     * The function returns the Gateway Socket of the record
     * @param key   Key of the record
     * @return      DatagramSocket of the record
     */
    private DatagramSocket getUdpDestination(KeyRecord key){
        return (DatagramSocket)(((Pair) udpTable.get(key)).first);
    }

    /**
     * The function returns the TTL of the record
     * @param key   Key of the record
     * @return      TTL of the record
     */
    private long getUdpTTL(KeyRecord key){
        return (long)(((Pair) udpTable.get(key)).second);
    }

}
