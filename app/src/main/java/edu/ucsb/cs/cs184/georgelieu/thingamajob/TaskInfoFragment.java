package edu.ucsb.cs.cs184.georgelieu.thingamajob;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends DialogFragment {

    private Task task;

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    /*
    private void displayFragment(Task task) {
        TaskInfoFragment taskInfoFragment = new TaskInfoFragment(task);
        tas

        kInfoFragment.show(getFragmentManager(), "Task info fragment");
    }
     */

    public TaskInfoFragment(Task task) {
        this.task = task;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView title = getView().findViewById(R.id.taskTitle);
        TextView user = getView().findViewById(R.id.user);
        TextView description = getView().findViewById(R.id.taskDescription);
        TextView date = getView().findViewById(R.id.taskDate);
        TextView cost = getView().findViewById(R.id.taskCost);

        title.setText(task.getTitle());
        user.setText(task.getOriginal_poster_email());
        description.setText(task.getDescription());

        // maybe do more with this
        date.setText(month(task.getMonth()) + " " + task.getDay() + ", " + task.getYear());

        // truncate to two values
        cost.setText("$ " + String.format("%.2f", task.getPay()));

        Button takeTask = getView().findViewById(R.id.takeTask);
        if(task.isFinished) {
            takeTask.setEnabled(false);
        } else {
            takeTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper.taskTaken(task.getTask_id());
                    dismiss();
                }
            });
        }

    }

    private String month(int x) {
        switch (x) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Jan";
        }


    }

}
