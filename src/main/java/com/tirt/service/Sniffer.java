package com.tirt.service;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

/**
 * Created by Kuba on 15.04.2016.
 */
public class Sniffer {

    private static final String COUNT_KEY
            = Sniffer.class.getName() + ".count";
    private static final int COUNT
            = Integer.getInteger(COUNT_KEY, 100);

    private static final String READ_TIMEOUT_KEY
            = Sniffer.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT
            = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY
            = Sniffer.class.getName() + ".snaplen";
    private static final int SNAPLEN
            = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    public void startSniffing(PcapNetworkInterface nif) throws PcapNativeException, NotOpenException {
        String filter = ""; //filter

        final PcapHandle handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        if (filter.length() != 0) {
            handle.setFilter(
                    filter,
                    BpfProgram.BpfCompileMode.OPTIMIZE
            );
        }

        PacketListener listener
                = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                System.out.println(handle.getTimestamp());
                System.out.println(packet);
            }
        };

        try {
            handle.loop(COUNT, listener);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PcapStat ps = handle.getStats();
        System.out.println("ps_recv: " + ps.getNumPacketsReceived());
        System.out.println("ps_drop: " + ps.getNumPacketsDropped());
        System.out.println("ps_ifdrop: " + ps.getNumPacketsDroppedByIf());
//    if (Platform.isWindows()) {
        System.out.println("bs_capt: " + ps.getNumPacketsCaptured());
//    }

        handle.close();
    }



}
