package com.project.sii.proxyapp;

import android.content.Intent;
import android.net.VpnService;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Hashtable;

/**
 *  This service allows to create a VPN if it doesn't exist one
 */
public class MyVPNService extends VpnService {

    /*  TAG */
    private static String TAG = "MyVPNService";
    /*  Thread's class instance */
    private Thread mThread;
    /*  Wrap of a file descriptor   */
    private ParcelFileDescriptor mInterface;
    /*  Builder for the interface   */
    Builder builder = new Builder();
    /*  One hashtable for every managed transport protocol  */
    Hashtable udpTable, tcpTable, icmpTable;

    /**
     * The method is used only when the service is instantiated
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyVPNService created");
        udpTable = new Hashtable();
        tcpTable = new Hashtable();
        icmpTable = new Hashtable();
    }

    /**
     * This function initializes the service interface. It creates a new session:
     * -    it creates of a new thread;
     * -    it configurates of the TUN and it gets the interface;
     * -    it queues outcoming packets in the input stream and it writes the incoming ones in the
     *      output stream;
     * -    it creates a @see DatagramChannel as the VPN tunnel;
     * -    it protects the socket;
     * -    it loops to pass packets.
     *
     * @param intent    Intent from @see MainActivity
     * @param flags     Additional data about this start request (default:  0)
     * @param startId   Integer representing this specific request to start
     * @return          START_STICKY for services that are explicitly started and stopped as needed.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyVPNService resumed");
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mInterface = builder.setSession("MyVPNService")
                            .addAddress("192.168.0.1", 24)
                            .addDnsServer("8.8.8.8")
                            .addRoute("0.0.0.0", 0).establish();
                    FileInputStream in = new FileInputStream(
                            mInterface.getFileDescriptor());
                    FileOutputStream out = new FileOutputStream(
                            mInterface.getFileDescriptor());
                    DatagramChannel tunnel = DatagramChannel.open();
                    tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
                    protect(tunnel.socket());
                    ByteBuffer packet = ByteBuffer.allocate(32767);
                    /*  It is used to determine the status of the tunnel:
                     *  -   positive value: sending;
                     *  -   otherwise: receiving;
                     */
                    while (true) {

                        }

                            // TODO TUZZIA
                            //  get packet with in
                            //  put packet to tunnel
                            //  get packet form tunnel
                            //  return packet with out



            } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Log.d("MyVpnService", "OK\n");
                        if (mInterface != null) {
                            mInterface.close();
                            mInterface = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "MyVpnRunnable");

        mThread.start();
        return START_STICKY;
    }

    /**
     * This function reads the outgoing packet from the input stream.
     * @param in        File input stream to read from
     * @param packet    bytebuffer used to read
     * @param tunnel    TUN to write to
     * @throws IOException  exception for reading from the input stream
     * @return              1 if it is all ok, timer otherwise
     */
    private int  readFromInput(FileInputStream in, ByteBuffer packet, DatagramChannel tunnel)
            throws IOException {
        // TODO rivedi
        int length = 0;
        length = in.read(packet.array());
        if (length > 0) {
            packet.limit(length);
            tunnel.write(packet);
            packet.clear();
        }
        return 0;
    }

    /**
     * This function reads the ingoing packet from tunnel.
     * @param out           File output stream to write to
     * @param packet        bytebuffer used to read
     * @param tunnel        TUN to read from
     * @throws IOException  exception for reading from the input stream
     * @return              0 if it is all ok, timer otherwise
     */
    private int readFromOutput(FileOutputStream out, ByteBuffer packet, DatagramChannel tunnel)
            throws IOException {
        // TODO rivedi, basta packet.get(0) != 0? Che vordi' switch to receiving?
        int length = tunnel.read(packet);
        if (length > 0) {
            if (packet.get(0) != 0) {
                out.write(packet.array(), 0, length);
            }
            packet.clear();
        }
        return 0;
    }

    @Override
    public void onDestroy(){
        if (mThread != null) {
            mThread.interrupt();
        }
        Log.d(TAG, "MyVPNService destroyed");
        super.onDestroy();
    }

    private void extractPacket(ByteBuffer packet) throws UnknownHostException {
        IPPktManager ipPkt = new IPPktManager(packet);
        InetAddress sourceAddress = ipPkt.getSourceAddress();
        InetAddress destinationAddress = ipPkt.getDestinationAddress();
        ByteBuffer payload = ipPkt.getPayload();
        int protocol = ipPkt.getTransportProtocol();

        // TODO Pkt management
        switch(protocol){
            case IPPktManager.UDP:

                break;
            case IPPktManager.TCP:
                // TCP
                break;
            case IPPktManager.ICMP:
                // ICMP
                break;
        }

    }
}
