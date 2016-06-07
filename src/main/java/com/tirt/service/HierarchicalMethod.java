package com.tirt.service;

import com.tirt.api.ClusteringMethod;
import com.tirt.entity.Cluster;
import com.tirt.entity.Point;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 02.06.2016.
 */
public class HierarchicalMethod implements ClusteringMethod {

    private HierarchicalClusterer hierarchicalClusterer;
    private List<Point> points;

    public static void main(String[] args) {
        HierarchicalMethod hierarchicalMethod = new HierarchicalMethod();
        hierarchicalMethod.points = new ArrayList<>();
        hierarchicalMethod.points.add(new Point(2, 3));
        hierarchicalMethod.points.add(new Point(7, 13));
        hierarchicalMethod.points.add(new Point(21, 23));
        hierarchicalMethod.points.add(new Point(22, 23));
        hierarchicalMethod.points.add(new Point(4, 8));
        hierarchicalMethod.execute();
    }

    @Override
    public List<Cluster> execute() {
        hierarchicalClusterer = new HierarchicalClusterer();
        Instances instances = createInstances();
        buildClusterer(instances);
        String newickFormat = getNewickFormat();
        System.out.println(instances.toString());
        System.out.println(newickFormat);

        return convertNewickToList(newickFormat);
    }

    private List<Cluster> convertNewickToList(String newickFormat) {


        return null;
    }

    private void buildClusterer(Instances instances) {
        try {
            hierarchicalClusterer.buildClusterer(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Instances createInstances() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("x"));
        attributes.add(new Attribute("y"));
        Instances instances = new Instances("temp", attributes, 0);
        for(Point point : points) {
            double[] instanceValues = new double[instances.numAttributes()];
            instanceValues[0] = point.getX();
            instanceValues[1] = point.getY();
            instances.add(new DenseInstance(1.0, instanceValues));
        }
        return instances;
    }

    private String getNewickFormat() {
        try {
            return hierarchicalClusterer.graph();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String printNewickTree(String newickFormat) {
        return null;
    }
}
