package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class User {
    public String email;        /* primary key */
    public String full_name;

    public User(String email, String full_name, double star_rating, int number_of_ratings) {
        this.email = email;
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getFull_name() {
        return full_name;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", getEmail());
        result.put("full_name", getFull_name());
        return result;
    }
}
