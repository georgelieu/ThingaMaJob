package edu.ucsb.cs.cs184.georgelieu.thingamajob;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskInfoFragment extends DialogFragment {

    private static String TAG = "DialogFragment/Simon";

    private Task task;

    private double lat = 34.412936;
    private double lon = -119.847863;
    private String email = "simon@simon.com";

    public EditTaskInfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public EditTaskInfoFragment(double latitude, double longitude) {
        lat = latitude;
        lon = longitude;
    }

    @SuppressLint("ValidFragment")
    public EditTaskInfoFragment(Task task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_task_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        final TextView title = getView().findViewById(R.id.editTaskTitle);
        final TextView description = getView().findViewById(R.id.editTaskDescription);
        final TextView month = getView().findViewById(R.id.editTaskMonth);
        final TextView day = getView().findViewById(R.id.editTaskDay);
        final TextView year = getView().findViewById(R.id.editTaskYear);
        final TextView cost = getView().findViewById(R.id.editTaskCost);


        // set date as current date
        Date currentTime = Calendar.getInstance().getTime();
        month.setText("" + (currentTime.getMonth()+1));
        day.setText("" + currentTime.getDate());
        year.setText("" + (currentTime.getYear()+1900));

        if(this.task != null) {

            // we are editing a task
            title.setText(task.getTitle());
            description.setText(task.getDescription());

            // maybe do more with this
//            month.setText(task.getMonth());
//            day.setText(task.getDay());
//            year.setText(task.getYear());

            // truncate to two values
            cost.setText(String.format("%.2f", task.getPay()));

            final String id = task.getTask_id();

            Button saveTask = (Button) getView().findViewById(R.id.taskSave);
            saveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "save task button");
                    DatabaseHelper.updateTask(id,
                            title.getText().toString(),
                            description.getText().toString(),
                            cost.getText().toString(),
                            Integer.parseInt(year.getText().toString()),
                            Integer.parseInt(month.getText().toString()),
                            Integer.parseInt(day.getText().toString()));
                    dismiss();
                    Toast.makeText(getContext(), "Task saved.", Toast.LENGTH_SHORT).show();
                }
            });

            Button deleteButton = (Button) getView().findViewById(R.id.taskDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "delete task button");
                    DatabaseHelper.removeTask(id);
                    dismiss();
                    Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            // change text of save task button to create task
            Button createTask = (Button) getView().findViewById(R.id.taskSave);
            createTask.setText("Create Task");
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "create task button");

                    // make sure all fields are filled
                    if(title.getText().toString().isEmpty() ||
                            description.getText().toString().isEmpty() ||
                            cost.getText().toString().isEmpty() ||
                            year.getText().toString().isEmpty() ||
                            month.getText().toString().isEmpty() ||
                            day.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Log.d("task", "at this point, user with email " + MainActivity.current_user_email + " is gonna add new task");
                    Log.d("task", "at this point, user with full name " + MainActivity.current_user_full_name + " is gonna add new task");
                    DatabaseHelper.addNewTaskToDatabase(title.getText().toString(),
                            description.getText().toString(),
                            lon,
                            lat,
                            Double.valueOf(cost.getText().toString()),
                            Integer.parseInt(year.getText().toString()),
                            Integer.parseInt(month.getText().toString()),
                            Integer.parseInt(day.getText().toString()),
                            MainActivity.current_user_email);
                    Toast.makeText(getContext(), "Task created!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });

            // remove delete button
            Button deleteButton = getView().findViewById(R.id.taskDelete);
            deleteButton.setVisibility(View.GONE);

        }

    }

}
