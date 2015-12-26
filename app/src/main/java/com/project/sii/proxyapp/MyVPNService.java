package com.project.sii.proxyapp;

import android.content.Intent;
import android.net.VpnService;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *  This service allows to create a VPN if it doesn't exist one
 */
public class MyVPNService extends VpnService {

    /*  Thread's class instance */
    private Thread mThread;
    /*  Wrap of a file descriptor   */
    private ParcelFileDescriptor mInterface;
    /*  Builder for the interface   */
    Builder builder = new Builder();

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
                    int timer = 0;
                    while (true) {
                        // TO DO
                        //  get packet with in
                        //  put packet to tunnel
                        //  get packet form tunnel
                        //  return packet with out
                        Log.d("MyVpnService", "OK\n");
                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (mInterface != null) {
                            mInterface.close();
                            mInterface = null;
                        }
                    } catch (Exception e) {

                    }
                }
            }

        }, "MyVpnRunnable");

        mThread.start();
        return START_STICKY;
    }

    /**
     * This method reads the outgoing packet from the input stream.
     * @param in        File input stream to read from
     * @param packet    bytebuffer used to read
     * @param tunnel    TUN to write to
     * @param timer     integer used for timing
     * @throws IOException  exception for reading from the input stream
     */
    private void readOutPackets(FileInputStream in, ByteBuffer packet, DatagramChannel tunnel, int timer)
            throws IOException {
        int length = 0;
        length = in.read(packet.array());
        if (length > 0) {
            packet.limit(length);
            tunnel.write(packet);
            packet.clear();
            // If we were receiving, switch to sending.
            if (timer < 1) {
                timer = 1;
            }
        }
    }

    /**
     * This method reads the ingoing packet from tunnel
     * @param out       File output stream to write to
     * @param packet    bytebuffer used to read
     * @param tunnel    TUN to read from
     * @param timer     integer used for timing
     * @throws IOException  exception for reading from the input stream
     */
    private void readInPackets(FileOutputStream out, ByteBuffer packet, DatagramChannel tunnel, int timer)
            throws IOException {
        int length = tunnel.read(packet);
        if (length > 0) {
            if (packet.get(0) != 0) {
                out.write(packet.array(), 0, length);
            }
            packet.clear();
            // If we were sending, switch to receiving.
            if (timer > 0) {
                timer = 0;
            }
        }
    }

    @Override
    public void onDestroy(){
        if (mThread != null) {
            mThread.interrupt();
        }
        Log.d("MyVpnService", "Destroy\n");
        super.onDestroy();
    }
}
