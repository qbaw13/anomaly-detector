package com.tirt.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

/**
 * Created by Kuba on 15.04.2016.
 */
public class Sniffer extends Service<Void>{

    private PcapNetworkInterface pcapNetworkInterface;
    private PcapStat pcapStat;
    private int packetsCount;

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


    @Override
    protected Task<Void> createTask() {
        String filter = "";
        PcapHandle handle = openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        if (filter.length() != 0) {
            setHandleFilter(handle, filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        }
        PacketListener listener = createListener(handle);
        startLoop(handle, listener);
        setStats(handle);
        handle.close();
        return null;
    }
    private PcapHandle openLive(int snaplen, PcapNetworkInterface.PromiscuousMode promiscuous, int readTimeout) {
        try {
            return pcapNetworkInterface.openLive(snaplen, promiscuous, readTimeout);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHandleFilter(PcapHandle handle, String filter, BpfProgram.BpfCompileMode compileMode) {
        try {
            handle.setFilter(filter, compileMode);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

    private PacketListener createListener(PcapHandle handle) {
        return new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                System.out.println(handle.getTimestamp());
                System.out.println(packet);
            }
        };
    }

    private void startLoop(PcapHandle handle, PacketListener listener) {
        try {
            handle.loop(packetsCount, listener);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

    private void setStats(PcapHandle handle) {
        try {
            setPcapStat(handle.getStats());
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

    public void setPcapNetworkInterface(PcapNetworkInterface pcapNetworkInterface) {
        this.pcapNetworkInterface = pcapNetworkInterface;
    }

    public PcapNetworkInterface getPcapNetworkInterface() {
        return pcapNetworkInterface;
    }


    public PcapStat getPcapStat() {
        return pcapStat;
    }

    public void setPcapStat(PcapStat pcapStat) {
        this.pcapStat = pcapStat;
    }

    public void setPacketsCount(int packetsCount) {
        this.packetsCount = packetsCount;
    }
}
