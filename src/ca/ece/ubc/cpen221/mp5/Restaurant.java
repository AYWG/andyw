package ca.ece.ubc.cpen221.mp5;

import java.util.List;

// This class represents a Yelp restaurant.
public class Restaurant {

    // Rep invariant: All fields (type, business_id, ... , price) != null, 
    // stars >= 0, review_count >= 0, price > 0
    // Abs function: Maps Restaurant object to a restaurant described in the Yelp Academic Dataset
    private final String type;
    private final String business_id;
    private final String name;
    private List<String> neighborhoods;
    private final String full_address;
    private final String city;
    private final String state;
    private final double latitude;
    private final double longitude;
    private final double stars;
    private long review_count;
    private final String photo_url;
    private List<String> categories;
    private boolean open;
    private List<String> schools;
    private final String url;
    private final long price;

    public Restaurant(String type, String business_id, String name,
            List<String> neighborhoods, String full_address, String city,
            String state, double latitude, double longitude, double stars,
            long review_count, String photo_url, List<String> categories,
            boolean open, List<String> schools, String url, long price) {
        this.type = type;
        this.business_id = business_id;
        this.name = name;
        this.neighborhoods = neighborhoods;
        this.full_address = full_address;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
        this.review_count = review_count;
        this.photo_url = photo_url;
        this.categories = categories;
        this.open = open;
        this.schools = schools;
        this.url = url;
        this.price = price;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getName() {
        return this.name;
    }

    public long getPrice() {
        return this.price;
    }

    public double getRating() {
        return this.stars;
    }

    public String getBizID() {
        return this.business_id;
    }

    public List<String> getCategories() {
        return this.categories;
    }
    
    public boolean getOpen(){
        return this.open;
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public List<String> getNeighborhoods(){
        return this.neighborhoods;
    }
    
    public String getState(){
        return this.state;
    }
    
    public String getType(){
        return this.type;
    }
    
    public double getStars(){
        return this.stars;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public String getFullAdress(){
        return this.full_address;
    }
    
    public List<String> getSchools(){
        return this.schools;
    }
    
    public long getReviewCount(){
        return this.review_count;
    }
    
    public String getPhotoUrl(){
        return this.photo_url;
    }
}
