package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.List;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/*
 * Feature function that gets a Restaurant's category (represented as a double value)
 */

public class RestaurantCategory implements MP5Function {

    
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        
        List<List<String>> allCategories = db.getCategories();
        
        
        for (int i = 0; i < allCategories.size(); i++) {
            
            // find the category/categories in allCategories that matches the
            // category/categories of yelpRestaurant, then return i, where i is
            // an arbitrarily mapped double value from allCategories
            if (yelpRestaurant.getCategories().containsAll(allCategories.get(i))) {
                return (double) i;
            }
        }
        
        // should never reach this point
        return 0.0;
    }

}
