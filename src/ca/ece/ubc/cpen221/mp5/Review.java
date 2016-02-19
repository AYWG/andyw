package ca.ece.ubc.cpen221.mp5;

import java.util.List;
import java.util.Map;

// This class represents a Yelp review.

public class Review {

    private final String type;
    private final String business_id;
    private final String user_id;
    private final String review_id;
    private final long stars;
    private final String text;
    private final String date;
    private Map<String, Integer> votes;

    public Review(String type, String business_id, String user_id,
            String review_id, long stars, String text, String date,
            Map<String, Integer> votes) {
        this.type = type;
        this.business_id = business_id;
        this.user_id = user_id;
        this.review_id = review_id;
        this.stars = stars;
        this.text = text;
        this.date = date;
        this.votes = votes;
    }

    public String getUserID() {
        return this.user_id;
    }

    public String getBizID() {
        return this.business_id;
    }

    public long getRating() {
        return this.stars;
    }
    
    public String getReview() {
        return this.text;
    }
    
    public String getDate(){
        return this.date;
    }
}
