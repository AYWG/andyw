package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/*
 * Feature function that gets a Restaurant's price
 */

public class RestaurantPrice implements MP5Function {
     
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        
        return (double) yelpRestaurant.getPrice();
    }
}
