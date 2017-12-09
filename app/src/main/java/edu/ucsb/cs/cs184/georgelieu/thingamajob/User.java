package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class User {
    public String user_id;
    public String email;
    public String full_name;

    public User(String user_id, String email, String full_name) {
        this.user_id = user_id;
    public String email;        /* primary key */
    public String full_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
        result.put("user_id", getUser_id());
        result.put("email", getEmail());
        result.put("full_name", getFull_name());
        return result;
    }
}
