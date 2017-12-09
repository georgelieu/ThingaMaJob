package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class User {
    public String email;        /* primary key */
    public String full_name;
    public double star_rating;
    public int number_of_ratings;

    public User(String email, String full_name, double star_rating, int number_of_ratings) {
        this.email = email;
        this.full_name = full_name;
        this.star_rating = star_rating;
        this.number_of_ratings = number_of_ratings;
    }

    public String getEmail() {
        return email;
    }

    public String getFull_name() {
        return full_name;
    }

    public double getStar_rating() {
        return star_rating;
    }

    public double getNumber_of_ratings() {
        return number_of_ratings;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setStar_rating(double star_rating) {
        this.star_rating = star_rating;
    }

    public void setNumber_of_ratings(int number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", getEmail());
        result.put("full_name", getFull_name());
        result.put("star_rating", getStar_rating());
        result.put("number_of_ratings", getNumber_of_ratings());

        return result;
    }
}
