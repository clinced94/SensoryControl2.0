package com.fyp.david.sensorycontrolv2.actionFragments;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DAVID on 3/31/2017.
 */

public class RatingsAndUses {

    int rating;
    int uses;

    public RatingsAndUses(int rating, int uses) {
        this.rating = rating;
        this.uses = uses;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("userRating", rating);
        result.put("number_of_uses", uses);
        return result;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
