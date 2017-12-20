package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thien Hoang on 12/20/2017.
 */

public class TaskArrayAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;
    private boolean isPostedTasks;

    public TaskArrayAdapter(@NonNull Context context, @NonNull List<Task> tasks, boolean isPostedTasks) {
        super(context, R.layout.listview_row_tasks_posted, tasks);
        this.context = context;
        this.tasks = tasks;
        this.isPostedTasks = isPostedTasks;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (isPostedTasks) {
            View rowView = inflater.inflate(R.layout.listview_row_tasks_posted, null, true);
            ImageView status_img = (ImageView) rowView.findViewById(R.id.listview_status_img);
            TextView title = (TextView) rowView.findViewById(R.id.listview_task_title);
            TextView datePosted = (TextView) rowView.findViewById(R.id.listview_task_date_posted);
            Task task = tasks.get(position);
            title.setText(task.getTitle());
            datePosted.setText(String.format("%s-%s-%s", task.getMonth(), task.getDay(), task.getYear()));
            if (task.getIsFinished()) {
                status_img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.list_view_task_done, null));
                status_img.setColorFilter(ContextCompat.getColor(context, R.color.listview_task_done_background), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                status_img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.list_view_task_not_done, null));
            }
            return rowView;
        }
        else {
            View rowView = inflater.inflate(R.layout.listview_row_tasks_done, null, true);
            TextView title = (TextView) rowView.findViewById(R.id.listview_task_done_title);
            TextView datePosted = (TextView) rowView.findViewById(R.id.listview_task_done_date_posted);
            Task task = tasks.get(position);
            title.setText(task.getTitle());
            datePosted.setText(String.format("%s-%s-%s", task.getMonth(), task.getDay(), task.getYear()));
            return rowView;
        }
    }
}