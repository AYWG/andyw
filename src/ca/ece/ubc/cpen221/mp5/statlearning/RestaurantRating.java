package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/*
 * Feature function that gets a Restaurant's mean rating
 */

public class RestaurantRating implements MP5Function {
    
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        return yelpRestaurant.getRating();
    }
}
