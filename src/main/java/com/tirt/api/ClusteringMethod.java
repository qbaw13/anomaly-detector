package com.tirt.api;

import com.tirt.entity.Cluster;

import java.util.List;

/**
 * Created by Kuba on 18.04.2016.
 */
public interface ClusteringMethod {

    public List<Cluster> execute();
}
