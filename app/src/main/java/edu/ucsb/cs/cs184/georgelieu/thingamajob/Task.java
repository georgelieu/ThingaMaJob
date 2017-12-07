package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import java.util.Date;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class Task {
    private int task_id; /* primary key */
    private Date time_posted; /* date, year, hour/minute timestamp */
    private String title;
    private String description;
    private double pay;
    private User original_poster;
    private User task_doer;
    private Boolean finished;
    private double op_to_doer_star_rating;
    private double doer_to_op_star_rating;

    private double longitude;
    private double latitude;

}
