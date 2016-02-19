package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/**
 * This class represents an MP5Function that predicts a user's ratings.
 */

public class RatingPredictor implements MP5Function {

    // Rep Invariant: the ith value in feature corresponds to the ith value in
    // ratings. Also, feature.size() == ratings.size()
    // Abs Function: feature maps to the set of known inputs; ratings maps to
    // the set of known outputs. Together, they are used to implement least-squares
    // linear regression for predicting a user's ratings.
    private List<Double> feature = new ArrayList<Double>();
    private List<Double> ratings = new ArrayList<Double>();

    // Fields for sums of squares (initial values will change after calling
    // non-empty constructor
    // Note that 'x' corresponds to values in feature, and 'y' corresponds to
    // values in ratings
    private double Sxx = 0.0;
    private double Syy = 0.0;
    private double Sxy = 0.0;

    private double meanX = 0.0;
    private double meanY = 0.0;

    // Regression coefficients
    private double a;
    private double b;

    // The featureFunction that we want to use to compute new inputs
    MP5Function featureFunction;

    // True only if size of feature/ratings is 1
    private boolean cannotPredictRating = false;

    public RatingPredictor(Map<Double, Double> data,
            MP5Function featureFunction) {

        for (double feat : data.keySet()) {
            feature.add(feat);
            ratings.add(data.get(feat));
        }

        // If we only have value in feature (i.e. the user only had one review)
        // then we cannot compute least squares regression
        if (feature.size() == 1) {
            cannotPredictRating = true;
        }

        // Compute meanX
        for (double feat : feature) {
            meanX += feat;
        }
        meanX /= feature.size();

        // Compute meanY
        for (double rating : ratings) {
            meanY += rating;
        }
        meanY /= ratings.size();

        // Compute Sxx
        for (int i = 0; i < feature.size(); i++) {
            Sxx += Math.pow((feature.get(i) - meanX), 2);
        }

        // Compute Syy
        for (int i = 0; i < ratings.size(); i++) {
            Syy += Math.pow((ratings.get(i) - meanY), 2);
        }

        // Compute Sxy
        for (int i = 0; i < feature.size(); i++) {
            Sxy += (feature.get(i) - meanX) * (ratings.get(i) - meanY);
        }

        if (!cannotPredictRating) {
            // Compute b
            b = Sxy / Sxx;

            // Compute a
            a = meanY - b * meanX;
        }

        this.featureFunction = featureFunction;
    }

    public double f(Restaurant yelpRestaurant, RestaurantDB db) {

        // If user has only one review, we only return the rating pertaining to that
        // review, regardless of inputs
        if (cannotPredictRating) {
            return ratings.get(0);
        }

        // Given some yelpRestaurant, return a predicted rating
        return a + b * featureFunction.f(yelpRestaurant, db);
    }

    /**
     * Returns this predictor's R^2 value, which estimates the accuracy of the
     * predictor. Returns 0 if cannotPredictRating is true
     * 
     * @return this predictor's R^2 value, or 0 if cannotPredictRating is true
     */
    public double getRSquared() {
        
        if (cannotPredictRating) {
            return 0.0;
        }
        return Math.pow(Sxy, 2) / (Sxx * Syy);
    }

}
