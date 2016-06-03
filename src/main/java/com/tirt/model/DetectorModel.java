package com.tirt.model;

import com.tirt.api.Clusterer;
import com.tirt.api.EClusteringMethod;
import com.tirt.service.ClustererImpl;
import com.tirt.utility.NetworkInterfaceReceiver;
import com.tirt.service.Sniffer;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorModel {

    public List<PcapNetworkInterface> receiveNetworkInterfaces() {
        try {
            return NetworkInterfaceReceiver.receiveNetworkInterfaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clusterer createClusterer(EClusteringMethod selectedMethod, int clusterCount) {
        //if(selectedMethod = kmneans)
            // return new Clusterre(..)
        return null;
    }

    public void startClusterer(Clusterer clusterer) {
        ((ClustererImpl) clusterer).start();
    }

    public Sniffer createSniffer(PcapNetworkInterface pcapNetworkInterface, int packetsCount) {
        Sniffer sniffer = new Sniffer();
        sniffer.setPcapNetworkInterface(pcapNetworkInterface);
        sniffer.setPacketsCount(packetsCount);
        return sniffer;
    }

    public void startSniffer(Sniffer sniffer) {
        sniffer.start();
    }
}
