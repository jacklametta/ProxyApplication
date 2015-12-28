package com.project.sii.proxyapp;

import android.content.Intent;
import android.net.VpnService;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
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
                        boolean isFree = true;
                        if ((timer = readFromInput(in, packet, tunnel, timer)) == 1)
                            isFree = false;
                        if ((timer = readFromOutput(out, packet, tunnel, timer)) == 0)
                            isFree = false;

                        // If we are idle or waiting for the network, sleep for a
                        // fraction of time to avoid busy looping.
                        if (isFree) {
                            Thread.sleep(100);
                            // Increase the timer. This is inaccurate but good enough,
                            // since everything is operated in non-blocking mode.
                            timer += (timer > 0) ? 100 : -100;
                            // We are receiving for a long time but not sending.
                            if (timer < -15000) {
                                // Send empty control messages.
                                packet.put((byte) 0).limit(1);
                                for (int i = 0; i < 3; ++i) {
                                    packet.position(0);
                                    tunnel.write(packet);
                                }
                                packet.clear();
                                // Switch to sending.
                                timer = 1;
                            }
                            // We are sending for a long time but not receiving.
                            if (timer > 20000) {
                                throw new IllegalStateException("Timed out");
                            }
                        }

                            // TO DO
                            //  get packet with in
                            //  put packet to tunnel
                            //  get packet form tunnel
                            //  return packet with out

                            Log.d("MyVpnService", "OK\n");
                        }
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                    try {
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
     * @param timer     integer used for timing
     * @throws IOException  exception for reading from the input stream
     * @return              1 if it is all ok, timer otherwise
     */
    private int  readFromInput(FileInputStream in, ByteBuffer packet, DatagramChannel tunnel, int timer)
            throws IOException {
        int length = 0;
        length = in.read(packet.array());
        if (length > 0) {
            packet.limit(length);
            tunnel.write(packet);
            packet.clear();
            // If we were receiving, switch to sending.
            if (timer < 1) {
                return timer = 1;
            }
        }
        return timer;
    }

    /**
     * This function reads the ingoing packet from tunnel.
     * @param out           File output stream to write to
     * @param packet        bytebuffer used to read
     * @param tunnel        TUN to read from
     * @param timer         integer used for timing
     * @throws IOException  exception for reading from the input stream
     * @return              0 if it is all ok, timer otherwise
     */
    private int readFromOutput(FileOutputStream out, ByteBuffer packet, DatagramChannel tunnel, int timer)
            throws IOException {
        int length = tunnel.read(packet);
        if (length > 0) {
            if (packet.get(0) != 0) {
                out.write(packet.array(), 0, length);
            }
            packet.clear();
            // If we were sending, switch to receiving.
            if (timer > 0) {
                return timer = 0;
            }
        }
        return timer;
    }

    @Override
    public void onDestroy(){
        if (mThread != null) {
            mThread.interrupt();
        }
        Log.d("MyVpnService", "Destroy\n");
        super.onDestroy();
    }

    private void extractPacket(ByteBuffer packet) throws UnknownHostException {
        IPPktManager ipPkt = new IPPktManager(packet);
        InetAddress sourceAddress = ipPkt.getSourceAddress();
        InetAddress destinationAddress = ipPkt.getDestinationAddress();
        ByteBuffer payload = ipPkt.getPayload();
        int protocol = ipPkt.getTransportProtocol();
        long ttl = ipPkt.getTtl();

        // TO DO
        switch(protocol){
            case 17:
                // UDP
                break;
            case 6:
                // TCP
                break;
            case 1:
                // ICMP
                break;
        }

    }

}
