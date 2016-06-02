package com.tirt.api;

import javafx.scene.chart.Chart;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public interface Clusterer {

    public void setClusteringMethod(ClusteringMethod clusteringMethod);

    public ClusteringMethod getClusteringMethod();

    public void setCharts(List<Chart> charts);

    public List<Chart> getCharts();

    public void startDetecting();

    public void pauseDetecting();

    public void stopDetecting();

    public Long getDetectingTime();

    public enum DetectingStatus {
        NOT_STARTED,
        DETECTING,
        PAUSED,
        FINISHED
    }

}
