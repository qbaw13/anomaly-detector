package com.tirt.service;

import com.tirt.api.ClusteringMethod;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
 

public class KMeansMethod implements ClusteringMethod {
 
	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 6;    
    //Number of Points
    private int NUM_POINTS = 50;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 10;
    
    private List<Point> points;
    private List<Cluster> clusters;
    
    public KMeansMethod() {
    	this.points = new ArrayList<Point>();
    	this.clusters = new ArrayList<Cluster>();    	
    }
    
//    public static void main(String[] args) {
//
//        KMeansMethod kmeans = new KMeansMethod();
//        kmeans.init();
//        kmeans.execute();
//    }
    
    //Initializes the process
//    public void init() {
//    	//Create Points
//    	points = Point.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
//
//    	//Create Clusters
//    	//Set Random Centroids
//    	for (int i = 0; i < NUM_CLUSTERS; i++) {
//    		Cluster cluster = new Cluster(i);
//    		Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
//    		cluster.setCentroid(centroid);
//    		clusters.add(cluster);
//    	}
//
//    	//Print Initial state
//    	plotClusters();
//    }
 
	private void plotClusters() {
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = clusters.get(i);
            cluster.plotCluster();
    	}
    }

    @Override
    public void execute() {
        double distance = -1; //dla pierwszego przebiegu
        int iteration = 0;
        List<Point> lastCentroids = new ArrayList<Point>();
        List<Point> currentCentroids = new ArrayList<Point>();

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(isCentroidsChanged(distance)) {
            //Clear cluster state
            clearClusters();

            lastCentroids.clear();
            lastCentroids.addAll(getCentroids());

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            currentCentroids.clear();
            currentCentroids.addAll(getCentroids());

            //Calculates total distance between new and old Centroids
            distance = distanceBetweenNewAndOldCentroids(lastCentroids, currentCentroids);

            //Displaying
            plotIteration(iteration, distance);
            plotClusters();

        }
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
    	List<Point> centroids = new ArrayList<Point>(NUM_CLUSTERS);
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
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
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
    		distance += Point.distance(lastCentroids.get(i), currentCentroids.get(i));
    	}
    	return distance;
    }
    
    private void plotIteration(int iteration, double distance){
    	DecimalFormat df = new DecimalFormat("#.##");
    	System.out.println("#################");
    	System.out.println("Iteration: " + iteration);
    	System.out.println("Centroid distances: " + df.format(distance));
    }

}