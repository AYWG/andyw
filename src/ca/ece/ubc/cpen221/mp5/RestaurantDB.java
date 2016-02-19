package ca.ece.ubc.cpen221.mp5;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.print.resources.serviceui;


// This class represents the Restaurant Database (consisting of
// data from the Yelp Academic Dataset).

@SuppressWarnings("unchecked")
public class RestaurantDB {

    // Rep invariant: restaurants, reviews, users != null
    // All Restaurant, Review, and User objects are stored in same order as the
    // order that they are listed in their respective JSON files
    // Abs function: Maps RestaurantDB to Yelp's Academic Dataset 
    
    // For storing all Restaurant objects
    private List<Restaurant> restaurants;

    // For storing all Review objects
    private List<Review> reviews; 

    // For storing all User objects
    private List<User> users; 

    /**
     * Create a database from the Yelp dataset given the names of three files:
     * <ul>
     * <li>One that contains data about the restaurants;</li>
     * <li>One that contains reviews of the restaurants;</li>
     * <li>One that contains information about the users that submitted reviews.
     * </li>
     * </ul>
     * The files contain data in JSON format.
     * 
     * @param restaurantJSONfilename
     *            the filename for the restaurant data
     * @param reviewsJSONfilename
     *            the filename for the reviews
     * @param usersJSONfilename
     *            the filename for the users
     */
    public RestaurantDB(String restaurantJSONfilename,
            String reviewsJSONfilename, String usersJSONfilename) {
        
        JSONParser parser = new JSONParser();
        BufferedReader br = null;
        restaurants = new ArrayList<Restaurant>();
        // Process data for restaurants
        try {

            br = new BufferedReader(new FileReader(restaurantJSONfilename));
            Object obj;
            String s;
            while ((s = br.readLine()) != null ) {

                    obj = parser.parse(s);
                    JSONObject jsonObject = (JSONObject) obj;

                    String type = (String) jsonObject.get("type");
                    String business_id = (String) jsonObject.get("business_id");
                    String name = (String) jsonObject.get("name");
                    JSONArray neighborhoods = (JSONArray) jsonObject.get("neighborhoods");
                    String full_address = (String) jsonObject.get("full_address");
                    String city = (String) jsonObject.get("city");
                    String state = (String) jsonObject.get("state"); 
                    double latitude = (double) jsonObject.get("latitude");
                    double longitude = (double) jsonObject.get("longitude");
                    double stars = (double) jsonObject.get("stars");
                    long review_count = (long) jsonObject.get("review_count");
                    String photo_url = (String)jsonObject.get("photo_url");
                    JSONArray categories = (JSONArray) jsonObject.get("categories");
                    boolean open = (boolean) jsonObject.get("open");
                    JSONArray schools = (JSONArray) jsonObject.get("schools");  
                    String url = (String)jsonObject.get("url");
                    long price = (long) jsonObject.get("price");
                    
                    restaurants.add(new Restaurant(type, business_id, name, neighborhoods, 
                            full_address, city, state, latitude, longitude, stars, 
                            review_count, photo_url, categories, open, schools, url, price));
                } 


        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Process data for reviews
        reviews = new ArrayList<Review>();
        
        try {

            br = new BufferedReader(new FileReader(reviewsJSONfilename));
            Object obj;
            String s;
            while ((s = br.readLine()) != null ) {

                    obj = parser.parse(s);
                    JSONObject jsonObject = (JSONObject) obj;

                    String type = (String) jsonObject.get("type");
                    String business_id = (String) jsonObject.get("business_id");
                    String user_id = (String) jsonObject.get("user_id");
                    String review_id = (String)jsonObject.get("review_id"); 
                    long stars = (long) jsonObject.get("stars");
                    String text = (String) jsonObject.get("text");
                    String date = (String) jsonObject.get("date");
                    JSONObject votes = (JSONObject)jsonObject.get("votes");
                    
                   reviews.add(new Review(type, business_id, user_id, review_id, stars, 
                                           text, date, votes));
                   
                } 

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Process data for users
        users = new ArrayList<User>();
        try {

            br = new BufferedReader(new FileReader(usersJSONfilename));
            Object obj;
            String s;
            while ((s = br.readLine()) != null ) {

                    obj = parser.parse(s);
                    JSONObject jsonObject = (JSONObject) obj;

                    String type = (String) jsonObject.get("type");
                    String user_id = (String) jsonObject.get("user_id");
                    String name = (String) jsonObject.get("name");
                    String url = (String) jsonObject.get("url"); 
                    long review_count = (long) jsonObject.get("review_count");
                    double average_stars = (double) jsonObject.get("average_stars");
                    JSONObject votes = (JSONObject) jsonObject.get("votes");
                    
                   users.add(new User(type, user_id, name, url, review_count, 
                                       average_stars, votes));
                   
                } 

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
    /**
     * 
     * @param queryString
     *            a String representing the query that is not empty
     * @return a Set of Restaurants that match the requirements of the
     *         queryString
     */
    public Set<Restaurant> query(Queue<String> queryString) {
        Set<Restaurant> rest = null;
        Set<Restaurant> secondRest;
        Set<Restaurant> finalRest = null;
        long high;
        while (!queryString.isEmpty()) {

            while (!queryString.isEmpty()) {
                String query = queryString.peek();
                if (!query.equals("&&") || !query.equals("||")) {
                    rest = recursion(queryString);
                }

                while (!queryString.peek().equals("&&") || !queryString.peek().equals("||")) {
                    queryString.poll();
                }

                if (query.equals("&&")) {
                    queryString.poll();
                    secondRest = query(queryString);
                    while (!queryString.peek().equals("&&") || !queryString.peek().equals("||")) {
                        queryString.poll();
                    }
                    for (Restaurant res : rest) {
                        if (secondRest.contains(rest))
                            finalRest.add(res);
                    }
                    rest = finalRest;
                }

                if (query.equals("||")) {
                    queryString.poll();
                    secondRest = query(queryString);
                    while (!queryString.peek().equals("&&") || !queryString.peek().equals("||")) {
                        queryString.poll();
                    }
                    rest.addAll(secondRest);
                }
            }
        }
        return rest;
    }
    
    /*
     * @requires Queue String that is not empty
     * @returns a set of Restaurant that matches the command
     */
    
    public Set<Restaurant> recursion(Queue<String> queryString) {
        long low;
        long high;
        Set<Restaurant> seaRest = null;
        String query = queryString.peek();
        String location;
        String category;

        if (!query.equals("&&") || !query.equals("||")) {
            if (query.equals("price")) {
                low = Long.parseLong(queryString.poll());
                high = Long.parseLong(queryString.poll());
                seaRest = QuaryRichOperations.price(getRestaurants(), low, high);
                return seaRest;
            }

            if (query.equals("in")) {
                location = queryString.poll();
                seaRest = QuaryRichOperations.in(getRestaurants(), location);
                return seaRest;
            }

            if (query.equals("category")) {
                category = queryString.poll();
                seaRest = QuaryRichOperations.category(getRestaurants(), category);
                return seaRest;
            }
        }
        return seaRest;

    }
    
    
    /**
     * @return a set of restaurants consisting of those stored in the database
     */
    public Set<Restaurant> getRestaurants() {
        Set<Restaurant> allRestaurants = new HashSet<Restaurant>();
        for (Restaurant rstrnt : restaurants) {
            allRestaurants.add(rstrnt);
        }
        return allRestaurants;
    }
    
    /**
     * @return a set of reviews consisting of those stored in the database
     */
    public Set<Review> getReviews() {
        Set<Review> allReviews = new HashSet<Review>();
        for (Review rev : reviews) {
            allReviews.add(rev);
        }
        return allReviews;
    }
    
    /**
     * @return a set of users consisting of those stored in the database
     */
    public Set<User> getUsers() {
        Set<User> allUsers = new HashSet<User>();
        for (User user : users) {
            allUsers.add(user);
        }
        return allUsers;
    }
    /**
     * 
     * @return a list of all categories stored in the database
     */
    
    public List<List<String>> getCategories() {
        
        List<List<String>> allCategories = new ArrayList<List<String>>();
        for (Restaurant rstrnt : restaurants) {
            allCategories.add(rstrnt.getCategories());
        }
        
        return allCategories;
    }
    
    public void addRestaurant(String type, String business_id, String name, List<String> neighborhoods,
            String full_address, String city, String state, double latitude, double longitude, double stars,
            long review_count, String photo_url, List<String> categories, boolean open, List<String> schools,
            String url, long price) {

        restaurants.add(new Restaurant(type, business_id, name, neighborhoods, full_address, city, state, latitude,
                longitude, stars, review_count, photo_url, categories, open, schools, url, price));

    }

    public void addReview(String type, String business_id, String user_id, String review_id, long stars, String text, String date,
            Map<String, Integer> votes) {

        reviews.add(new Review(type, business_id, user_id, review_id, stars, text, date, votes));

    }
    
    public void addUser(String type, String user_id, String name, String url, long review_count,
            double average_stars, Map<String, Integer> votes) {

        users.add(new User(type, user_id, name, url, review_count, average_stars, votes));

    }
    
}
