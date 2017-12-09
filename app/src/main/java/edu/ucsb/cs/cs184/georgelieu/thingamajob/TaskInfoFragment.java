package edu.ucsb.cs.cs184.georgelieu.thingamajob;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        date.setText(task.getMonth() + " " + task.getDay() + ", " + task.getYear());

        // truncate to two values
        cost.setText("$" + task.getPay());

    }

}