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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */

public class ClustererImpl extends Service<Void> implements Clusterer{

    private ClusteringMethod clusteringMethod;
    private List<Cluster> clusters;

    public ClustererImpl () {
        clusters = new ArrayList<>();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                setClusters(clusteringMethod.execute());
                return null;
            }
        };

    }

    public void setClusteringMethod(ClusteringMethod clusteringMethod) {
        this.clusteringMethod = clusteringMethod;
    }

    public ClusteringMethod getClusteringMethod() {
        return clusteringMethod;
    }

    @Override
    public void setData(List<Point> points) {
        clusteringMethod.setPoints(points);
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    private void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

}
