package com.tirt.model;

import com.tirt.api.Clusterer;
import com.tirt.api.ClusteringMethod;
import com.tirt.api.EClusteringMethod;
import com.tirt.service.ClustererImpl;
import com.tirt.service.HierarchicalMethod;
import com.tirt.service.KMeansMethod;
import com.tirt.utility.NetworkInterfaceReceiver;
import com.tirt.service.Sniffer;
import org.pcap4j.core.PcapNetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorModel {

    private  static  Logger LOGGER = LoggerFactory.getLogger(DetectorModel.class);

    public List<PcapNetworkInterface> receiveNetworkInterfaces() {
        try {
            return NetworkInterfaceReceiver.receiveNetworkInterfaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clusterer createClusterer(EClusteringMethod selectedMethod, int clusterCount) {
        Clusterer clusterer = new ClustererImpl();
        ClusteringMethod clusteringMethod = null;

        if(selectedMethod.equals(EClusteringMethod.K_MEANS)) {
            clusteringMethod = new KMeansMethod(clusterCount);
            ((KMeansMethod) clusteringMethod).setClusterCount(clusterCount);
        }
        else if(selectedMethod.equals(EClusteringMethod.HIERARCHICAL)) {
            clusteringMethod = new HierarchicalMethod();
        }

        clusterer.setClusteringMethod(clusteringMethod);

        return clusterer;
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
        LOGGER.info("Sniffer started");
    }
}
