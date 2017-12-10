package edu.ucsb.cs.cs184.georgelieu.thingamajob;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            TextView title = getView().findViewById(R.id.editTaskTitle);
            TextView description = getView().findViewById(R.id.editTaskDescription);

            LinearLayout dateHolder = getView().findViewById(R.id.dateHolder);
            // nested in view
//            TextView month = getView().findViewById(R.id.editTaskMonth);
//            TextView day = getView().findViewById(R.id.editTaskDay);
//            TextView year = getView().findViewById(R.id.editTaskYear);
            TextView cost = getView().findViewById(R.id.editTaskCost);

            title.setText(task.getTitle());
            description.setText(task.getDescription());

            // maybe do more with this
//            month.setText(task.getMonth());
//            day.setText(task.getDay());
//            year.setText(task.getYear());

            // truncate to two values
            cost.setText("$ " + String.format("%.2f", task.getPay()));

            final String id = task.getTask_id();


            Button saveTask = (Button) getView().findViewById(R.id.taskSave);
            saveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "save task button");


                }
            });

            Button deleteButton = (Button) getView().findViewById(R.id.taskDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "delete task button");
                    DatabaseHelper.removeTask(id);

                }
            });
//
        } else {
//
//            // we are creating a new task
//
//            // remove delete button
            Button deleteButton = getView().findViewById(R.id.taskDelete);
            deleteButton.setVisibility(View.GONE);
//
//            /*
//            ViewGroup layout = (ViewGroup) deleteButton.getParent();
//            if(null!=layout) {
//                layout.removeView(deleteButton);
//            }
//            */
//
//            final TextView title = getView().findViewById(R.id.editTaskTitle);
//            final TextView description = getView().findViewById(R.id.editTaskDescription);
//            final TextView month = getView().findViewById(R.id.editTaskMonth);
//            final TextView day = getView().findViewById(R.id.editTaskDay);
//            final TextView year = getView().findViewById(R.id.editTaskYear);
//            final TextView cost = getView().findViewById(R.id.editTaskCost);
//
//            Date currentTime = Calendar.getInstance().getTime();
//
//            // TODO : get current location
//            /*
//            double lon;
//            double lat;
//             */
//
//            // TODO : standardize cost input
//
//            Log.i("Simon", currentTime.toString());
//
//            month.setText("" + (currentTime.getMonth()+1));
//            day.setText("" + currentTime.getDate());
//            year.setText("" + (currentTime.getYear()+1900));
//
            Button createTask = (Button) getView().findViewById(R.id.taskSave);
            createTask.setText("Create Task");
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "create task button");

//                    DatabaseHelper.addNewTaskToDatabase(title.getText().toString(),
//                            description.getText().toString(),
//                            lon,
//                            lat,
//                            Double.valueOf(cost.getText().toString()),
//                            Integer.parseInt(year.getText().toString()),
//                            Integer.parseInt(month.getText().toString()),
//                            Integer.parseInt(day.getText().toString()),
//                            email);
                }
            });

        }

    }

}
