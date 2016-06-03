package com.tirt.utility;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 15.04.2016.
 */
public class NetworkInterfaceReceiver {

    public static List<PcapNetworkInterface> receiveNetworkInterfaces() throws IOException {
        List<PcapNetworkInterface> allDevs = null;
        try {
            allDevs = Pcaps.findAllDevs();

        } catch (PcapNativeException e) {
            throw new IOException(e.getMessage());
        }
        return allDevs;
    }
}
