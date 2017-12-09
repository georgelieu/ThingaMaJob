package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by xuewensherryli on 12/8/17.
 */

public class DatabaseHelper {
    public static DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference mUserCloudEndPoint;
    public static DatabaseReference mTaskCloudEndPoint;

    public static void initializeDatabase() {
        mUserCloudEndPoint = mDatabaseReference.child("Users");
        mTaskCloudEndPoint = mDatabaseReference.child("Tasks");
    }

    // TODO: Upon creation of a user account, make new user for them

    public static void addNewUserToDatabase(String email, String full_name){
        User new_user = new User(email, full_name, 0.0 /*star_rating*/, 0 /*number_of_ratings*/);
        String user_key = mUserCloudEndPoint.push().getKey();
        Map<String, Object> userValues = new_user.toMap();
        mUserCloudEndPoint.child(user_key).setValue(userValues);
    }

    public static void addNewTaskToDatabase(String title, String description, double longitude, double latitude, double pay, int year, int month, int day, String original_poster_email) {
        String task_key = mTaskCloudEndPoint.push().getKey();
        Task new_task = new Task(task_key, title, description, longitude, latitude, pay, year, month, day, original_poster_email, null /*task_doer_email*/, 0.0 /*op_to_doer_star_rating*/, 0.0 /*doer_to_op_star_rating*/, false /*isFinished*/);
        Map<String, Object> taskValues = new_task.toMap();
        mTaskCloudEndPoint.child(task_key).setValue(taskValues);
    }

    // get All tasks
    public static void getAllTasks(){

    }

    // add update database fordoer, ratings
}
