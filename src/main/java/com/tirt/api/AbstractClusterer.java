package com.tirt.api;

import com.tirt.entity.Point;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.Chart;

import java.util.List;

/**
 * Created by Kuba on 02.06.2016.
 */
public class AbstractClusterer {


    protected Task<Void> createTask() {
        return null;
    }


    public void setClusteringMethod(ClusteringMethod clusteringMethod) {

    }

    public ClusteringMethod getClusteringMethod() {
        return null;
    }


    public void setData(List<Point> points) {

    }
}
