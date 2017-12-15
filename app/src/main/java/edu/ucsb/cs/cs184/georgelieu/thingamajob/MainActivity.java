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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static String current_user_email = "";
    public static String current_user_full_name = "";
    public static String current_user_id = "";

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
                    current_user_email = user.getEmail();
                    setCurrent_user_full_name_and_user_id(current_user_email);
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

    public static void setCurrent_user_full_name_and_user_id(String current_user_email) {
        // query database for user info to set current_user information in DatabaseHelper
        Query current_user = DatabaseHelper.mDatabaseReference.child("Users").orderByChild("email").equalTo(current_user_email);

        current_user.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    queryUserInfo(child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public static void queryUserInfo(final String userId){
        Query user_info = DatabaseHelper.mDatabaseReference.child("Users").child(userId);
        user_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fullname = dataSnapshot.child("full_name").getValue().toString();
                current_user_id = userId;
                current_user_full_name = fullname;
                Log.d("aaa", current_user_full_name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
