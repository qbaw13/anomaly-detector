package com.tirt.entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {
 
    private double x = 0;
    private double y = 0;
    private double size = 0;
    private int cluster_number = 0;
 
    public Point(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getX()  {
        return this.x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setCluster(int n) {
        this.cluster_number = n;
    }
    
    public int getCluster() {
        return this.cluster_number;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    //Calculates the distance between two points.
    public static double distance(Point p, Point centroid, double maxX, double maxY) {
        //System.out.println("parametry: " + maxX + "," + maxY);
        double firstAttributeWeight = maxX / maxY;
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2)*firstAttributeWeight + Math.pow((centroid.getX() - p.getX()), 2));
    }

    //Creates random point
//    public static Point createRandomPoint() {
//    	Random r = new Random();
//
//    	double x = min + (max - min) * r.nextDouble();
//    	double y = min + (max - min) * r.nextDouble();
//
//    	return new Point(x,y);
//    }
//
//    public static List<Point> createRandomPoints(int min, int max, int number) {
//    	List points = new ArrayList(number);
//    	for(int i = 0; i < number; i++) {
//    		points.add(createRandomPoint(min,max));
//    	}
//    	return points;
//    }
//
    public String toString() {
    	DecimalFormat df = new DecimalFormat("#.##");
    	return "(" + df.format(x) + "; " + df.format(y) + ")";
    }

    public static Point createRandomPoint(double minX, double minY, double maxX, double maxY) {
        Random r = new Random();

    	double x = minX + (maxX - minX) * r.nextDouble();
    	double y = minY + (maxY - minY) * r.nextDouble();

    	return new Point(x,y);
    }
}