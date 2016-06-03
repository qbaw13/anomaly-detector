package com.tirt.api;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.Chart;

import java.util.List;

/**
 * Created by Kuba on 02.06.2016.
 */
public class AbstractClusterer extends Service<Void> implements Clusterer {

    @Override
    protected Task<Void> createTask() {
        return null;
    }

    @Override
    public void setClusteringMethod(ClusteringMethod clusteringMethod) {

    }

    @Override
    public ClusteringMethod getClusteringMethod() {
        return null;
    }
}
