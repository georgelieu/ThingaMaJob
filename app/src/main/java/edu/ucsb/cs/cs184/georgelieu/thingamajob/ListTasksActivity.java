package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    private List<Task> tasks;
    private boolean isPostedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        isPostedTasks = intent.getBooleanExtra("isPostedTasks", true);
        setTitle(isPostedTasks ? "Tasks Posted" : "Tasks Done");
        setContentView(R.layout.activity_list_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (isPostedTasks) {
            DatabaseHelper.getAllTasksPostedByUser(MainActivity.current_user_email, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tasks = new ArrayList<>();
                    for( DataSnapshot child : dataSnapshot.getChildren()) {
                        tasks.add(child.getValue(Task.class));
                    }
                    populateListView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
        else {
            DatabaseHelper.getAllTasksDoneByUser(MainActivity.current_user_email, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tasks = new ArrayList<>();
                    for( DataSnapshot child : dataSnapshot.getChildren()) {
                        tasks.add(child.getValue(Task.class));
                    }
                    populateListView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static Intent ListTasksActivityIntentFactory(Context c, boolean isPostedTasks) {
        Intent intent = new Intent(c, ListTasksActivity.class);
        intent.putExtra("isPostedTasks", isPostedTasks);
        return intent;
    }

    private void populateListView() {
        TaskArrayAdapter taskArrayAdapter = new TaskArrayAdapter(this, tasks, isPostedTasks);
        ListView tasks_list = (ListView) findViewById(R.id.tasks_list);
        tasks_list.setAdapter(taskArrayAdapter);
        tasks_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                if (!task.getIsFinished()) {
                    EditTaskInfoFragment editTaskInfoFragment = new EditTaskInfoFragment(task);
                    editTaskInfoFragment.show(getFragmentManager(), "Edit task info fragment");
                } else {
                    TaskInfoFragment taskInfoFragment = new TaskInfoFragment(task);
                    taskInfoFragment.show(getFragmentManager(), "Task info fragment");
                }
            }
        });
        if(tasks.isEmpty()) {
            TextView tv = (TextView) findViewById(R.id.no_tasks_msg);
            tv.setText("No Results to Show");
        } else {
            TextView tv = (TextView) findViewById(R.id.no_tasks_msg);
            tv.setText("Completed!");
        }
    }
}
