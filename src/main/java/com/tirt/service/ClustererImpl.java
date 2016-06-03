package com.tirt.service;

import com.tirt.api.AbstractClusterer;
import com.tirt.api.Clusterer;
import com.tirt.api.ClusteringMethod;
import com.tirt.entity.Cluster;
import com.tirt.entity.Point;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public class ClustererImpl extends Service<Void> {

    private ClusteringMethod clusteringMethod;
    private List<Cluster> clusters;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {



                return null;
            }
        };

    }

    public void setClusteringMethod(ClusteringMethod clusteringMethod) {

    }

    public ClusteringMethod getClusteringMethod() {
        return null;
    }
}
