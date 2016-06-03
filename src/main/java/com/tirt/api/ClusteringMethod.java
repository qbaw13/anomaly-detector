package com.tirt.api;

import com.tirt.entity.Cluster;
import com.tirt.entity.Point;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public interface ClusteringMethod {

    List<Cluster> execute();

    void setPoints(List<Point> points);
}
