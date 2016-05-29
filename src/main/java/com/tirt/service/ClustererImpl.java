package com.tirt.service;

import com.tirt.api.Clusterer;
import com.tirt.api.ClusteringMethod;
import javafx.scene.chart.Chart;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public class ClustererImpl implements Clusterer {


    @Override
    public void setClusteringMethod(ClusteringMethod clusteringMethod) {

    }

    @Override
    public ClusteringMethod getClusteringMethod() {
        return null;
    }

    @Override
    public void setCharts(List<Chart> charts) {

    }

    @Override
    public List<Chart> getCharts() {
        return null;
    }

    @Override
    public void startDetecting() {

    }

    @Override
    public void pauseDetecting() {

    }

    @Override
    public void stopDetecting() {

    }

    @Override
    public Long getDetectingTime() {
        return null;
    }
}
