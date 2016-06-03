package com.tirt.api;

import com.tirt.entity.Cluster;
import com.tirt.entity.Point;
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

    void setData(List<Point> points);

    List<Cluster> getClusters();

    public enum DetectingStatus {
        NOT_STARTED,
        DETECTING,
        PAUSED,
        FINISHED
    }

}
