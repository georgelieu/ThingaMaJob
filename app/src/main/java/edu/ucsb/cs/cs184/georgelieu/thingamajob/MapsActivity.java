package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MapsActivity/Simon";

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationClient;
    public static double longitude;
    public static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // to get location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up Naviagation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set FAB to open create task dialog
        FloatingActionButton addTask = (FloatingActionButton) findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        Log.d( TAG, "Simon " + location.getLongitude() + " " + location.getLatitude());
                                        MapsActivity.latitude = location.getLatitude();
                                        MapsActivity.longitude = location.getLongitude();
                                    } else {
                                        Log.d(TAG, "location not found");
                                    }
                                }
                            });

                    // allow user to make new task
                    EditTaskInfoFragment editTaskInfoFragment = new EditTaskInfoFragment(latitude, longitude);
                    editTaskInfoFragment.show(getFragmentManager(), "Edit task info fragment");
                }
            }
        });

        // set up DB listener for tasks
        setTaskListener();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ucsb = new LatLng(34.412936, -119.847863);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucsb));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                displayTaskInfoFragment(marker);
                return false;
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }

    public void addMarker(Task task) {
        LatLng position = new LatLng(task.getLatitude(), task.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions().position(position));
    }

    public void displayTaskInfoFragment(Marker marker) {
        // get task info from marker
        Task task = markerToTask.get(marker);

        if(task.getOriginal_poster_email() != MainActivity.current_user_email) {
            // if you're clicking on someone else's task
            TaskInfoFragment taskInfoFragment = new TaskInfoFragment(task);
            taskInfoFragment.show(getFragmentManager(), "Task info fragment");
        } else {
            // if you're clicking on your own task
            EditTaskInfoFragment editTaskInfoFragment = new EditTaskInfoFragment(task);
            editTaskInfoFragment.show(getFragmentManager(), "Edit task info fragment");
        }
    }

    // so that on marker click, we know which task to inflate
    private static HashMap<Marker, Task> markerToTask = new HashMap<>();

    // so that on task info edit, we know which marker->task link to update
    private static HashMap<String, Marker> keyToMarker = new HashMap<>();

    public void setTaskListener() {

        DatabaseHelper.mTaskCloudEndPoint.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // add marker to map

                String key = dataSnapshot.getKey();
                Task newTask = dataSnapshot.getValue(Task.class);

                LatLng position = new LatLng(newTask.latitude, newTask.longitude);
                Marker marker = mMap.addMarker(new MarkerOptions().position(position));

                markerToTask.put(marker, newTask);
                keyToMarker.put(key, marker);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // update marker's task

                String key = dataSnapshot.getKey();
                Task newTask = dataSnapshot.getValue(Task.class);

                Marker markerToUpdate = keyToMarker.get(key);

                markerToTask.put(markerToUpdate, newTask);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // remove marker

                String key = dataSnapshot.getKey();

                Marker markerToRemove = keyToMarker.get(key);
                markerToRemove.remove();

                markerToTask.remove(markerToRemove);
                keyToMarker.remove(key);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                // should never happen

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // hopefully should never happen
                Log.d(TAG, databaseError.toString());

            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_posted:
                intent = ListTasksActivity.ListTasksActivityIntentFactory(MapsActivity.this, true);
                startActivity(intent);
                break;
            case R.id.nav_done:
                intent = ListTasksActivity.ListTasksActivityIntentFactory(MapsActivity.this, false);
                startActivity(intent);
                break;
            case R.id.nav_sign_out:
                signOut();
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
