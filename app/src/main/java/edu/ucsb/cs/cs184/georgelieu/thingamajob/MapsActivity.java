package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String TAG = "MapsActivity/Simon";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set FAB to open create task dialog
        FloatingActionButton addTask = (FloatingActionButton) findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // allow user to make new task
                EditTaskInfoFragment editTaskInfoFragment = new EditTaskInfoFragment();
                editTaskInfoFragment.show(getFragmentManager(), "Edit task info fragment");
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

    void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    public void addMarker(Task task) {
        LatLng position = new LatLng(task.getLatitude(), task.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions().position(position));
    }

    public void displayTaskInfoFragment(Marker marker) {
        // get task info from marker
        Task task = markerToTask.get(marker);

//        if(task.getOriginal_poster_email() != DatabaseHelper.getCurrentUserEmail()) {
        if(false) {
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



}
