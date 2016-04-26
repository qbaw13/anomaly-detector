package com.tirt.api;

/**
 * Created by Kuba on 20.04.2016.
 */
public enum EClusteringMethod {
    K_MEANS("k-means"),
    HIERARCHICAL("hierarchical");

    private final String text;

    private EClusteringMethod(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
