package com.tirt.service;

import com.tirt.api.ClusteringMethod;
import com.tirt.entity.Cluster;
import com.tirt.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;


public class KMeansMethod implements ClusteringMethod {

    private static Logger LOGGER = LoggerFactory.getLogger(KMeansMethod.class);

    private int clusterCount;
    private List<Point> points;
    private List<Cluster> clusters;
    //private double firstAttributeWeight;
    //private double secondAttributeWeight;
    private double firstAttributeMax;
    private double secondAttributeMax;
    
    public KMeansMethod(int clusterCount) {
        this.clusterCount = clusterCount;
    	this.points = new ArrayList<>();
    	this.clusters = new ArrayList<>();
    }
 
	private void plotClusters() {
    	for (int i = 0; i < clusterCount; i++) {
    		Cluster cluster = clusters.get(i);
            cluster.plotCluster();
    	}
    }

    public void init() {
        //Set Random Centroids
        for (int i = 0; i < clusterCount; i++) {
            Cluster cluster = new Cluster(i);


            Comparator<Point> comparatorX = (Point p1, Point p2) -> new Double(p1.getX()+"").compareTo(p2.getX());
            Point pointWithMinX = Collections.min(points, comparatorX);
            Point pointWithMaxX = Collections.max(points, comparatorX);

            System.out.println("max x to: " + pointWithMaxX.getX());
            firstAttributeMax = pointWithMaxX.getX();
            Comparator<Point> comparatorY = (Point p1, Point p2) -> new Double(p1.getY()+"").compareTo(p2.getY());
            Point pointWithMinY = Collections.min(points, comparatorY);
            Point pointWithMaxY = Collections.max(points, comparatorY);
            System.out.println("max y to: " + pointWithMaxY.getY());
            secondAttributeMax = pointWithMaxY.getY();

            Point centroid = Point.createRandomPoint(pointWithMinX.getX(), pointWithMinY.getY(), pointWithMaxX.getX(), pointWithMaxY.getY());
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
    }

    @Override
    public List<Cluster> execute() {
        LOGGER.info("KMeans method started");
        double distance = -1;
        int iteration = 0;
        List<Point> lastCentroids = new ArrayList<>();
        List<Point> currentCentroids = new ArrayList<>();

        while(isCentroidsChanged(distance)) {
            clearClusters();
            lastCentroids.clear();
            lastCentroids.addAll(getCentroids());

            assignCluster();

            calculateCentroids();

            iteration++;

            currentCentroids.clear();
            currentCentroids.addAll(getCentroids());

            distance = distanceBetweenNewAndOldCentroids(lastCentroids, currentCentroids);

            plotIteration(iteration, distance);
            plotClusters();
        }

        return clusters;
    }

    @Override
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    private boolean isCentroidsChanged(double distance){
    	return distance != 0;
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List<Point> getCentroids() {
    	List<Point> centroids = new ArrayList<Point>(clusterCount);
    	for(Cluster cluster : clusters) {
    		Point aux = cluster.getCentroid();
    		Point point = new Point(aux.getX(), aux.getY());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	min = max;
            for(int i = 0; i < clusterCount; i++) {
            	Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid(), firstAttributeMax, secondAttributeMax);
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> pointsInsideThisCluster = cluster.getPoints();
            int n_points = pointsInsideThisCluster.size();
            
            for(Point point : pointsInsideThisCluster) {
            	sumX += point.getX();
                sumY += point.getY();
            }
            
            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newX = sumX / n_points;
            	double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
    
    private double distanceBetweenNewAndOldCentroids(List<Point> lastCentroids, List<Point> currentCentroids){
    	double distance = 0;
    	for(int i = 0; i < lastCentroids.size(); i++) {
    		distance += Point.distance(lastCentroids.get(i), currentCentroids.get(i), firstAttributeMax, secondAttributeMax);
    	}
    	return distance;
    }
    
    private void plotIteration(int iteration, double distance){
    	DecimalFormat df = new DecimalFormat("#.##");
    	System.out.println("#################");
    	System.out.println("Iteration: " + iteration);
    	System.out.println("Centroid distances: " + df.format(distance));
    }

    public void setClusterCount(int clusterCount) {
        this.clusterCount = clusterCount;
    }
}