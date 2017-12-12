package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper.initializeDatabase();

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
                    System.out.println("Thinks he's logged out...");
                    // User is signed out
                    String className = this.getClass().getName();
                    if (!(className == "LoginActivity")) {
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

}
