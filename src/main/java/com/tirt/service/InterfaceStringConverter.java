package com.tirt.service;

import com.tirt.model.DetectorModel;
import javafx.util.StringConverter;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 15.04.2016.
 */
public class InterfaceStringConverter extends StringConverter<PcapNetworkInterface> {

    DetectorModel detectorModel = new DetectorModel();

    @Override
    public String toString(PcapNetworkInterface networkInterface) {
        return networkInterface.getDescription();
    }

    @Override
    public PcapNetworkInterface fromString(String string) {
        List<PcapNetworkInterface> interfaces = null;
        try {
            interfaces = detectorModel.obtainInterfaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (PcapNetworkInterface networkInterface : interfaces) {
            if(networkInterface.getDescription().equals(string)){
                return networkInterface;
            }
        }
        return null;
    }
}
