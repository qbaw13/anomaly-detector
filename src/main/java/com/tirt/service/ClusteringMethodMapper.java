package com.tirt.service;

import com.tirt.api.EClusteringMethod;

/**
 * Created by Kuba on 20.04.2016.
 */
public class ClusteringMethodMapper {

    public static EClusteringMethod map(String text) {
        for (EClusteringMethod method : EClusteringMethod.values()) {
            if(text.equals(method.toString())){
                return method;
            }
        }
        return null;
    }
}
