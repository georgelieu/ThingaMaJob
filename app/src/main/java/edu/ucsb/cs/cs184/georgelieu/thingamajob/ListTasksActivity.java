package edu.ucsb.cs.cs184.georgelieu.thingamajob;

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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    private List<Task> tasks;
    private boolean isPostedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        tasks = (List<Task>) intent.getSerializableExtra("tasks");
        isPostedTasks = intent.getBooleanExtra("isPostedTasks", true);
        TaskArrayAdapter taskArrayAdapter= new TaskArrayAdapter(this, tasks, isPostedTasks);
        setTitle(isPostedTasks ? "Tasks Posted" : "Tasks Done");
        setContentView(R.layout.activity_list_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        if(!tasks.isEmpty()) {
            TextView tv = (TextView) findViewById(R.id.no_tasks_msg);
            tv.setText("");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static Intent ListTasksActivityIntentFactory(Context c, List<Task> tasks, boolean isPostedTasks) {
        Intent intent = new Intent(c, ListTasksActivity.class);
        intent.putExtra("tasks", (Serializable) tasks);
        intent.putExtra("isPostedTasks", isPostedTasks);
        return intent;
    }
}
