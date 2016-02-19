package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class QuaryRichOperations {

    public static void startRichOperation(){
        
    }
    
    public static Set<Restaurant> price(Set<Restaurant> allRest,long low, long high){
        Set<Restaurant> pricedRest = new HashSet<Restaurant>();
        for (Restaurant rest : allRest){
            if (rest.getPrice() >= low && rest.getPrice() <= high){
                pricedRest.add(rest);
            }
        }
        return pricedRest;
    }
    
    public static Set<Restaurant> in(Set<Restaurant> allRest, String location){
        Set<Restaurant> neighborRest = new HashSet<Restaurant>();
        for (Restaurant rest : allRest){
            if (rest.getNeighborhoods().contains(location)){
                neighborRest.add(rest);
            }
        }
        return neighborRest;
    }
    
    public static Set<Restaurant> category(Set<Restaurant> allRest, String location){
        Set<Restaurant> catRest = new HashSet<Restaurant>();
        for (Restaurant rest : allRest){
            if (rest.getCategories().contains(location)){
                catRest.add(rest);
            }
        }
        return catRest;
    }
    
    
    
}
