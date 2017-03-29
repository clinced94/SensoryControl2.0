package com.fyp.david.sensorycontrolv2.actionFragments;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DAVID on 3/22/2017.
 */

public class ActionListItem {

    public String actionItemTitle;
    public String actionItemEffect;
    public String actionItemDescription;
    public String actionItemCreatedOn;
    public float actionItemRating;

    public ActionListItem() {

    }

    public ActionListItem(String title, String effect, String description) {

        this.actionItemTitle = title;
        this.actionItemEffect = effect;
        this.actionItemDescription = description;
        this.actionItemRating = 0;

        Date createdOn = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        this.actionItemCreatedOn = df.format(createdOn);
    }


    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("actionItemTitle", actionItemTitle);
        result.put("actionItemEffect", actionItemEffect);
        result.put("actionItemDescription", actionItemDescription);
        result.put("actionItemRating", actionItemRating);
        result.put("actionItemCreatedOn", actionItemCreatedOn);
        return result;
    }

    public String getActionItemTitle() {
        return actionItemTitle;
    }
    public void setActionItemTitle(String title) {
        this.actionItemTitle = title;
    }

    public String getActionItemEffect() {
        return actionItemEffect;
    }
    public void setActionItemEffect(String effect) {
        this.actionItemEffect = effect;
    }

    public String getActionItemDescription() {
        return actionItemDescription;
    }
    public void setActionItemDescription(String description) {
        this.actionItemDescription = description;
    }

    public float getActionItemRating() {
        return actionItemRating;
    }
    public void setActionItemRating(float rating) {
        this.actionItemRating = rating;
    }

    public String getActionItemCreatedOn() {
        return actionItemCreatedOn;
    }
    public void setActionItemCreatedOn(String createdOn) {
        this.actionItemCreatedOn = createdOn;
    }

    @Override
    public String toString() {
        String result = this.actionItemTitle + "\n" + this.actionItemEffect +
                "\n" + this.actionItemDescription + "\n" + this.actionItemRating + "\n" + this.actionItemCreatedOn;
        return result;
    }
}
