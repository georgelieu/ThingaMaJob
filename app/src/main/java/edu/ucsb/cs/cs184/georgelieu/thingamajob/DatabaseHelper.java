package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuewensherryli on 12/8/17.
 */

public class DatabaseHelper {
    public static DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference mUserCloudEndPoint;
    public static DatabaseReference mTaskCloudEndPoint;

    public static ArrayList<Task> mListOfTasks = new ArrayList<>();
    public static ArrayList<User> mListOfUsers = new ArrayList<>();
    public static HashMap<String, ArrayList<Task>> mDoer_To_Tasks = new HashMap<>();
    public static HashMap<String, ArrayList<Task>> mOriginalPoster_To_Tasks = new HashMap<>();

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

        mListOfUsers.add(new_user);
    }

    public static void addNewTaskToDatabase(String title, String description, double longitude, double latitude, double pay, int year, int month, int day, String original_poster_email) {
        String task_key = mTaskCloudEndPoint.push().getKey();
        Task new_task = new Task(task_key, title, description, longitude, latitude, pay, year, month, day, original_poster_email, null /*task_doer_email*/, false /*isFinished*/);
        Map<String, Object> taskValues = new_task.toMap();
        mTaskCloudEndPoint.child(task_key).setValue(taskValues);

        mListOfTasks.add(new_task);
    }

    // get All tasks
    public static ArrayList<Task> getAllTasks(){
        return mListOfTasks;
    }

    // push to database
    public static void updateTaskDoer(Task task, String email){
        final String doer_email = email;
        final String original_poster_email = task.getOriginal_poster_email();
        task.setTask_doer_email(email);

        mTaskCloudEndPoint.child(task.getTask_id()).child("task_doer_email").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(doer_email);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("database", "Task posted by " + original_poster_email + " has been assigned to " + doer_email);
            }
        });
    }

    public static void updateTaskIsFinished(Task task){
        final String original_poster_email = task.getOriginal_poster_email();
        final String doer_email = task.getTask_doer_email();
        task.setIsFinished(true);

        mTaskCloudEndPoint.child(task.getTask_id()).child("task_doer_email").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(true);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("database", "Task posted by " + original_poster_email + " has been finished by " + doer_email);
            }
        });
    }


}
