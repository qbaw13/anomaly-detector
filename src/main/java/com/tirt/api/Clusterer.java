package com.tirt.api;

import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.scene.chart.Chart;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public interface Clusterer {

    public void setClusteringMethod(ClusteringMethod clusteringMethod);

    public ClusteringMethod getClusteringMethod();

    public enum DetectingStatus {
        NOT_STARTED,
        DETECTING,
        PAUSED,
        FINISHED
    }

}
