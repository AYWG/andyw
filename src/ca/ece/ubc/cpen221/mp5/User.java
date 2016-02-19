package ca.ece.ubc.cpen221.mp5;

import java.util.List;
import java.util.Map;

// This class represents a Yelp user.

public class User {

    private final String type;
    private final String user_id;
    private final String name;
    private final String url;
    private long review_count;
    private double average_stars;
    
    private Map<String, Integer> votes;

    public User(String type, String user_id, String name, String url, long review_count,
            double average_stars, Map<String, Integer> votes) {
        this.type = type;
        this.user_id = user_id;
        this.name = name;
        this.url = url;
        this.review_count = review_count;
        this.average_stars = average_stars;
        this.votes = votes;
    }

    public String getUserID() {
        return this.user_id;
    }
}
