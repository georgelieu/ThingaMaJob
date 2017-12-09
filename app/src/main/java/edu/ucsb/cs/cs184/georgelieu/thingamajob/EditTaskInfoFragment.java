package edu.ucsb.cs.cs184.georgelieu.thingamajob;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskInfoFragment extends DialogFragment {

    private Task task;

    private double lat = 34.412936;
    private double lon = -119.847863;
    private String email = "simon@simon.com";

    public EditTaskInfoFragment() {
        // Required empty public constructor
    }

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

        if(this.task != null) {

            // we are editing a task
            TextView title = getView().findViewById(R.id.taskTitle);
            TextView user = getView().findViewById(R.id.user);
            TextView description = getView().findViewById(R.id.taskDescription);
            TextView month = getView().findViewById(R.id.taskMonth);
            TextView day = getView().findViewById(R.id.taskDay);
            TextView year = getView().findViewById(R.id.taskYear);
            TextView cost = getView().findViewById(R.id.taskCost);

            title.setText(task.getTitle());
            user.setText(task.getOriginal_poster_email());
            description.setText(task.getDescription());

            // maybe do more with this
            month.setText(task.getMonth());
            day.setText(task.getDay());
            year.setText(task.getYear());

            // truncate to two values
            cost.setText("$" + task.getPay());

            Button saveTask = (Button) getView().findViewById(R.id.taskSave);
            saveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        } else {

            // we are creating a new task

            // remove delete button
            Button deleteButton = getView().findViewById(R.id.taskDelete);
            deleteButton.setVisibility(View.GONE);

            /*
            ViewGroup layout = (ViewGroup) deleteButton.getParent();
            if(null!=layout) {
                layout.removeView(deleteButton);
            }
            */

            final TextView title = getView().findViewById(R.id.taskTitle);
            final TextView description = getView().findViewById(R.id.taskDescription);
            final TextView month = getView().findViewById(R.id.taskMonth);
            final TextView day = getView().findViewById(R.id.taskDay);
            final TextView year = getView().findViewById(R.id.taskYear);
            final TextView cost = getView().findViewById(R.id.taskCost);

            Date currentTime = Calendar.getInstance().getTime();

            // TODO : get current location
            /*
            double lon;
            double lat;
             */

            // TODO : standardize cost input

            Log.i("Simon", currentTime.toString());

            month.setText("" + (currentTime.getMonth()+1));
            day.setText("" + currentTime.getDate());
            year.setText("" + (currentTime.getYear()+1900));

            Button createTask = (Button) getView().findViewById(R.id.taskSave);
            createTask.setText("Create Task");
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseHelper.addNewTaskToDatabase(title.getText().toString(),
                            description.getText().toString(),
                            lon,
                            lat,
                            Double.valueOf(cost.getText().toString()),
                            Integer.parseInt(year.getText().toString()),
                            Integer.parseInt(month.getText().toString()),
                            Integer.parseInt(day.getText().toString()),
                            email);
                }
            });

        }

    }

}
