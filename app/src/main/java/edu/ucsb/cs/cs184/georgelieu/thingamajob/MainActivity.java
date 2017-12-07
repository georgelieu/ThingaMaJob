package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//    mDatabaseReference.child();


//    private DatabaseReference userCloudEndPoint;
//    private DatabaseReference taskCloudEndPoint;
//
//    userCloudEndPoint = mDatabaseReference.child("Users");
//    taskCloudEndPoint = mDatabaseReference.child("Tasks");
=======
>>>>>>> parent of aff8e9d... database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
