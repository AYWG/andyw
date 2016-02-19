package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class QueryOperations {

    public static String startOperation(RestaurantDB DB, String operation, String order) {
        String result = null;
        if (operation.equals("randomReview")) {
            result = randomReview(DB, order);
            return result;
        }

        if (operation.equals("getRestaurant")) {
            result = getRestaurant(DB, order);
            return result;
        }

        if (operation.equals("addRestaurant")) {
            if (addRestaurant(DB, order) == true) {
                result = "Restaurant " + order + " successfully added";
                return result;
            } else
                return result = "Restaurant not added";
        }

        if (operation.equals("addUser")) {
            if (addUser(DB, order) == true) {
                result = "User " + order + " successfully added";
                return result;
            } else
                return result = "User not added";
        }

        if (operation.equals("addReview")) {
            if (addReview(DB, order) == true) {
                result = "User " + order + " successfully added";
                return result;
            } else
                return result = "review not added";
        }
        return result;

    }

    public static String randomReview(RestaurantDB DB, String restName) {
        Set<Restaurant> Restaurants = new HashSet<Restaurant>(DB.getRestaurants());
        Set<Review> Reviews = new HashSet<Review>(DB.getReviews());
        String ID = "";
        String JSON = null;
        Restaurant target = null;
        for (Restaurant rest : Restaurants) {
            if (restName.equals(rest.getName())) {
                ID = rest.getBizID();
                target = rest;
            }
        }

        for (Review rev : Reviews) {
            if (rev.getBizID().equals(ID)) {
                JSON = "{\"type\": \"review\", \"buisness_id\": " + "\"" + rev.getBizID()
                        + "\", \"votes\" {\"cool\": }, \"review id\": " + "\"" + rev.getUserID() + "\"" + ", \"text\": "
                        + "\"" + rev.getReview() + "\"" + ", stars :" + String.valueOf(rev.getRating())
                        + ", \"user_id: \"" + "\"" + rev.getUserID() + "\"" + ", \"date\": " + "\"" + rev.getDate()
                        + "\"}";
                return JSON;
            }

        }

        return JSON = "Sorry we could not find a review for the given restaurant: " + RestJsonFormat(target);

    }

    public static String RestJsonFormat(Restaurant rest) {
        return "{\"open\": " + String.valueOf(rest.getOpen()) + ", \"url\": " + "\"" + rest.getUrl() + "\""
                + ", \"longitude\": " + String.valueOf(rest.getLongitude()) + ", neighborhoods :"
                + rest.getNeighborhoods().toString() + ", \"buisness_id: \"" + "\"" + rest.getBizID().toString() + "\""
                + ", \"name\": " + "\"" + rest.getName() + "\"" + ", \"categories\": " + rest.getCategories().toString()
                + ", \"state\": " + "\"" + rest.getState() + "\"" + ", \"type\": " + "\"" + rest.getType() + "\""
                + ", \"stars\": " + "\"" + String.valueOf(rest.getStars()) + "\"" + ", \"city\": " + "\""
                + rest.getCity() + "\"" + ", \"full_address\": " + "\"" + rest.getFullAdress() + "\""
                + ", \"review_count\": " + "\"" + rest.getReviewCount() + "\"" + ", \"photo_url\": " + "\""
                + rest.getPhotoUrl() + "\"" + ", \"schools\": " + rest.getSchools().toString() + ", \"latitude\": "
                + "\"" + rest.getLatitude() + "\"" + ", \"price\": " + "\"" + String.valueOf(rest.getPrice()) + "\""
                + "}";
    }

    public static String getRestaurant(RestaurantDB DB, String buisnessID) {
        String JSON = null;
        Set<Restaurant> Restaurants = new HashSet<Restaurant>(DB.getRestaurants());
        Set<Review> Reviews = new HashSet<Review>(DB.getReviews());
        for (Restaurant rest : Restaurants) {
            if (buisnessID.equals(rest.getBizID())) {
                JSON = RestJsonFormat(rest);
                return JSON;
            }
        }
        JSON = "Sorry we could not find the restaurant that matches your buisnessID";
        return JSON;
    }

    public static boolean addRestaurant(RestaurantDB DB, String restDetail) {
        Set<Restaurant> Restaurants = new HashSet<Restaurant>(DB.getRestaurants());
        JSONParser parser = new JSONParser();
        try {
            Object obj;

            obj = parser.parse(restDetail);
            JSONObject jsonObject = (JSONObject) obj;

            String type = (String) jsonObject.get("type");
            String business_id = (String) jsonObject.get("business_id");
            for (Restaurant rest : Restaurants) {
                if (business_id.equals(rest.getBizID()))
                    return false;
            }

            String name = (String) jsonObject.get("name");
            JSONArray neighborhoods = (JSONArray) jsonObject.get("neighborhoods");
            String full_address = (String) jsonObject.get("full_address");
            String city = (String) jsonObject.get("city");
            String state = (String) jsonObject.get("state");
            double latitude = (double) jsonObject.get("latitude");
            double longitude = (double) jsonObject.get("longitude");
            double stars = (double) jsonObject.get("stars");
            long review_count = (long) jsonObject.get("review_count");
            String photo_url = (String) jsonObject.get("photo_url");
            JSONArray categories = (JSONArray) jsonObject.get("categories");
            boolean open = (boolean) jsonObject.get("open");
            JSONArray schools = (JSONArray) jsonObject.get("schools");
            String url = (String) jsonObject.get("url");
            long price = (long) jsonObject.get("price");
            DB.addRestaurant(type, business_id, name, neighborhoods, full_address, city, state, latitude, longitude,
                    stars, review_count, photo_url, categories, open, schools, url, price);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean addReview(RestaurantDB DB, String reviewDetail) {
        Set<Review> Reviews = new HashSet<Review>(DB.getReviews());
        JSONParser parser = new JSONParser();
        try {

            Object obj;

            obj = parser.parse(reviewDetail);
            JSONObject jsonObject = (JSONObject) obj;

            String type = (String) jsonObject.get("type");
            String business_id = (String) jsonObject.get("business_id");
            for (Review rev : Reviews) {
                if (business_id.equals(rev.getBizID()))
                    return false;
            }
            String user_id = (String) jsonObject.get("user_id");
            String review_id = (String) jsonObject.get("review_id"); 
            long stars = (long) jsonObject.get("stars");
            String text = (String) jsonObject.get("text");
            String date = (String) jsonObject.get("date");
            JSONObject votes = (JSONObject) jsonObject.get("votes");
            DB.addReview(type, business_id, user_id, review_id, stars, text, date, votes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addUser(RestaurantDB DB, String userDetail) {
        Set<User> Users = new HashSet<User>(DB.getUsers());
        JSONParser parser = new JSONParser();
        try {

            Object obj;

            obj = parser.parse(userDetail);
            JSONObject jsonObject = (JSONObject) obj;

            String type = (String) jsonObject.get("type");
            String user_id = (String) jsonObject.get("user_id");
            for (User use : Users) {
                if (user_id.equals(use.getUserID()))
                    return false;
            }
            String name = (String) jsonObject.get("name");
            String url = (String) jsonObject.get("url"); 
            long review_count = (long) jsonObject.get("review_count");
            double average_stars = (double) jsonObject.get("average_stars");
            JSONObject votes = (JSONObject) jsonObject.get("votes");

            DB.addUser(type, user_id, name, url, review_count, average_stars, votes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
