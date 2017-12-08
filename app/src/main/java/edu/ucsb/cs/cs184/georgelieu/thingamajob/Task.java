package edu.ucsb.cs.cs184.georgelieu.thingamajob;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class Task {
    public int task_id;
    public String title;
    public String description;
    public double longitude;
    public double latitude;
    public double pay;
    public int year;
    public int month;
    public int day;

    public String original_poster_email; /* Use this key to display User's full_name and phone number as well*/
    public String task_doer_email = null;
    public double op_to_doer_star_rating = 0.0;
    public double doer_to_op_star_rating = 0.0; /* everytime this gets filled, we should average this into the doer’s “star_rating” field */
    public boolean finished = false;    /* indicates if task completed/uncompleted */
}
