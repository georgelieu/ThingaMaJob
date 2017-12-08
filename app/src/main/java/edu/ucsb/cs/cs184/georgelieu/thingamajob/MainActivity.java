package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    /* on first launch, show log in options, else, go directly to map */
    // TODO: integrate our map activity with simon & thien's map activity
    //private boolean firstLaunch = false;
    private DatabaseReference mUserCloudEndPoint;
    private DatabaseReference mTaskCloudEndPoint;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDatabase();

        //Firebase ref = new Firebase("https://thingamajob.firebaseio.com/");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                      Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    //intent.putExtra("EXTRA_SESSION_ID", sessionId);
                      startActivity(intent);
                     Log.d("LOG_Login", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    String className = this.getClass().getName();
                    if (!(className == "LoginActivity")) {
                        //Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);

                        // intent.putExtra("EXTRA_SESSION_ID", sessionId);
                        startActivity(intent);
                    }

                    Log.d("LOG_Login", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void initializeDatabase() {
        mUserCloudEndPoint = mDatabaseReference.child("Users");
        mTaskCloudEndPoint = mDatabaseReference.child("Tasks");

        mUserCloudEndPoint.setValue("Hello World").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("abc", e.getLocalizedMessage());
            }
        });

    }
}
