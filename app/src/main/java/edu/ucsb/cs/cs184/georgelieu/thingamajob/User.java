package edu.ucsb.cs.cs184.georgelieu.thingamajob;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class User {
    private String mUsername; /* primary key */
    private double mStarRating;
    private double mNumberOfRatings;
    private String mPhoneNumber;
    private String mEmail;

    public String getUsername() {
        return mUsername;
    }

    public double starRating() {
        return mStarRating;
    }

    public double getNumberOfRatings() {
        return mNumberOfRatings;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

}
