package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ca.ece.ubc.cpen221.mp5.*;
import javafx.scene.chart.PieChart.Data;

public class Algorithms {

    private static final int MAX_ITERATIONS = Integer.MAX_VALUE;
    
    /**
     * Use k-means clustering to compute k clusters for the restaurants in the
     * database. Precondition: k > 1
     * 
     * @param k
     *            the number of clusters
     * @param db
     *            the RestaurantDB object that contains information on all of
     *            the Restaurants
     * @return a List of Sets, where each Set contains all of the Restaurants
     *         corresponding to one cluster
     */
    public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {

        // Get all restaurants from RestaurantDB
        Set<Restaurant> restaurants = db.getRestaurants();

        // Represents all of the clusters involved, where each Centroid is
        // associated with a Set of Restaurants (cluster).
        Map<Centroid, Set<Restaurant>> clusters = new HashMap<Centroid, Set<Restaurant>>();

        // These initial values are somewhat arbitrary. 
        double minXBound = 9001;
        double maxXBound = -9001;
        double minYBound = 9001;
        double maxYBound = -9001;

        // Determine minimum x and y coordinates of the restaurants
        for (Restaurant rstrnt : restaurants) {
            if (rstrnt.getLatitude() < minXBound) {
                minXBound = rstrnt.getLatitude();
            }
            if (rstrnt.getLongitude() < minYBound) {
                minYBound = rstrnt.getLongitude();
            }
        }

        // Determine maximum x and y coordinates of the restaurants
        for (Restaurant rstrnt : restaurants) {
            if (rstrnt.getLatitude() > maxXBound) {
                maxXBound = rstrnt.getLatitude();
            }
            if (rstrnt.getLongitude() > maxYBound) {
                maxYBound = rstrnt.getLongitude();
            }
        }

        // Generate k random Centroids (within the coordinate bounds) and add them to clusters
        for (int i = 0; i < k; i++) {
            clusters.put(new Centroid(
                    minXBound + Math.random() * (maxXBound - minXBound),
                    minYBound + Math.random() * (maxYBound - minYBound), i),
                    new HashSet<Restaurant>());
        }
        
        // Loop that repeatedly groups restaurants based on distance to closest Centroid 
        // and updates the position of each Centroid accordingly. 
        // The loop finishes after every restaurant is closest to the Centroid that it is
        // currently assigned to, or after a set number of iterations.
        for (int iterations = 0; iterations <= MAX_ITERATIONS; iterations++) {
            
            boolean finished = true;
            boolean emptyClusters = false;

            // Check if clusters are "empty"
            for (Centroid centr : clusters.keySet()) {
                if (clusters.get(centr).isEmpty()) {
                    
                    // If any cluster is empty, we are not finished.  
                    emptyClusters = true;
                    finished = false;
                    break;
                }
            }

            // If clusters not empty, check if we're done;
            if (!emptyClusters) {
                
                // For each restaurant, determine ID of closest Centroid, then 
                // check if the Centroid's cluster contains that restaurant.
                // If not, we are not finished and must update clusters
                for (Restaurant rstrnt : restaurants) {
                    int closestCentroidId = getClosestCentroidID(rstrnt,
                            clusters.keySet());

                    for (Centroid centr : clusters.keySet()) {
                        if (centr.getID() == closestCentroidId) {
                            if (!clusters.get(centr).contains(rstrnt)) {
                                finished = false;
                                break;
                            }
                        }
                    }
                    if (!finished) {
                        break;
                    }
                }
            }
            
            // Only executes if we are finished, i.e. no cluster is empty
            // and each restaurant is assigned to its closest Centroid
            if (finished) {
                break;
            }
            
            // Since clustering is not finished if program reaches this point, begin
            // updating process:
            // clear all current restaurants assigned to each Centroid
            for (Centroid centr : clusters.keySet()) {
                clusters.get(centr).clear();
            }

            // Assign each restaurant to its closest Centroid
            for (Restaurant rstrnt : restaurants) {

                int closestCentroidId = getClosestCentroidID(rstrnt, clusters.keySet());

                for (Centroid centr : clusters.keySet()) {
                    if (centr.getID() == closestCentroidId) {
                        clusters.get(centr).add(rstrnt);
                        break;
                    }
                }
            }

            // Update centroid's x and y coordinates to be the average of
            // the coordinates of its cluster of restaurants
            for (Centroid centr : clusters.keySet()) {

                int sizeCluster = clusters.get(centr).size();
                double newX = 0.0;
                double newY = 0.0;

                for (Restaurant rstrnt : clusters.get(centr)) {
                    newX += rstrnt.getLatitude();
                    newY += rstrnt.getLongitude();
                }
                
                newX /= sizeCluster;
                newY /= sizeCluster;

                centr.setCoords(newX, newY);
            }
        }

        // We are finished; add all sets of Restaurants to new list and return that list
        List<Set<Restaurant>> listOfClusters = new ArrayList<Set<Restaurant>>();
        for (Centroid centr : clusters.keySet()) {
            listOfClusters.add(clusters.get(centr));
        }

        return listOfClusters;
    }

    /**
     * Computes the straight-line distance between a given Restaurant a Centroid
     * @param rstrnt a Restaurant
     * @param centr a Centroid
     * @returns the Euclidean distance between rstrnt and centr     
     */
    private static double getDistance(Restaurant rstrnt, Centroid centr) {

        // For simplicity purposes we treat a Restaurant's longitude as its y
        // coordinate and its latitude as its x coordinate
        double rX = rstrnt.getLatitude();
        double rY = rstrnt.getLongitude();
        double centroidX = centr.getX();
        double centroidY = centr.getY();

        return Math.sqrt(Math.pow((rX - centroidX), 2) + Math.pow((rY - centroidY), 2));

    }

    /**
     * Determines the ID of the closest Centroid to a given Restaurant
     * 
     * @param rstrnt a Restaurant
     * @param centrs a Set of Centroids
     * @return the ID of the Centroid in centrs that has the smallest distance to the Restaurant
     */
    private static int getClosestCentroidID(Restaurant rstrnt,
            Set<Centroid> centrs) {

        double smallestDistance = Double.MAX_VALUE;
        int closestCentroidID = -1;
        for (Centroid centr : centrs) {
            if (getDistance(rstrnt, centr) < smallestDistance) {
                smallestDistance = getDistance(rstrnt, centr);
                closestCentroidID = centr.getID();
            }

        }

        return closestCentroidID;

    }
    
    /**
     * Converts return value of kMeansClustering to JSON format
     * 
     * @param clusters a List of Sets of Restaurants, presumably generated from 
     * kMeansClustering
     * @return a String representing the k-means clustering data in JSON format
     */

    @SuppressWarnings("unchecked")
    public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
        
        JSONArray listClusters = new JSONArray();
        int cluster = 0;
        for (Set<Restaurant> rstrnts : clusters ) {

            for (Restaurant rstrnt : rstrnts) {
                JSONObject obj = new JSONObject();
                obj.put("x", rstrnt.getLatitude());
                obj.put("y", rstrnt.getLongitude());
                obj.put("name", rstrnt.getName());
                obj.put("cluster", cluster);
                obj.put("weight", 6.0);  // weight does not matter for this MP

                listClusters.add(obj);
            }

            cluster++;
        }
        
        return JSONValue.toJSONString(listClusters);
    }

    
    /**
     * This main method simply writes to voronoi.json with the clusters of 
     * restaurants formed from the method kMeansClustering (after converting 
     * the data to JSON format, of course)
     */
    public static void main(String[] args) {

        RestaurantDB db = new RestaurantDB("data/restaurants.json",
                "data/reviews.json", "data/users.json");

        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("visualize/voronoi.json"));
            bw.write(convertClustersToJSON(kMeansClustering(5, db)));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a user, feature function, and a restaurant database and returns a
     * function that predicts the user's ratings. 
     * 
     * @param u a User whose rating is being predicted
     * @param db the RestaurantDB from which information is obtained
     * @param featureFunction the function that extracts the relevant data from Restaurants
     * @return an MP5Function that predicts u's ratings for new Restaurants
     */
    public static MP5Function getPredictor(User u, RestaurantDB db,
            MP5Function featureFunction) {

        Map<Double, Double> predictionData = new HashMap<Double, Double>();
        
        // Search through reviews...
        for (Review review : db.getReviews()) {
            
            
            // Only care for reviews belonging to User u
            if (review.getUserID().equals(u.getUserID())) {
                
                // Search through restaurants...
                for (Restaurant rstrnt : db.getRestaurants()) {
                    
                    // Find the restaurant that matches the review
                    if (review.getBizID().equals(rstrnt.getBizID())) {
                        predictionData.put(featureFunction.f(rstrnt, db), (double) review.getRating());
                        break;
                    }
                }
                
            }
        }
        
        
        return new RatingPredictor(predictionData, featureFunction);
        
    }
    
    
    /**
     * Takes a user, a list of feature functions, and a restaurant database, and then returns
     * the function that best predict's the user's ratings 
     * 
     * @param u a User whose rating is being predicted
     * @param db the RestaurantDB from which information is obtained
     * @param featureFunctionList a list of MP5Functions that extract information from Restaurants
     * @return an MP5Function that best predicts u's ratings for new Restaurants
     */
    public static MP5Function getBestPredictor(User u, RestaurantDB db,
            List<MP5Function> featureFunctionList) {
        
        double currentBestRSquaredVal = 0.0;
        MP5Function r = null;
        
        for (MP5Function featureFunction : featureFunctionList) {
            RatingPredictor currentPredictor = (RatingPredictor) getPredictor(u, db, featureFunction);
            if (currentPredictor.getRSquared() > currentBestRSquaredVal ) {
                currentBestRSquaredVal = currentPredictor.getRSquared();
                r = currentPredictor;
            }
        }
        
        // r should never be null at this point
        return r;
    }
}