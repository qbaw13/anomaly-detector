package com.tirt.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorModel {

    public List<PcapNetworkInterface> obtainInterfaces() throws IOException {
        List<PcapNetworkInterface> allDevs = null;
        try {
            allDevs = Pcaps.findAllDevs();

        } catch (PcapNativeException e) {
            throw new IOException(e.getMessage());
        }
        return allDevs;
    }
}
