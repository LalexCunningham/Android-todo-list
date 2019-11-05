package com.example.todolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> taskTitle;
    private final ArrayList<String> taskDescription;
    private final ArrayList<Boolean> taskDone;

    public MyListAdapter(Activity context, ArrayList<String> taskTitle, ArrayList<String> taskDescription, ArrayList<Boolean> taskDone) {
        super(context, R.layout.mylist, taskTitle);

        this.context=context;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDone = taskDone;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView itemTitle = (TextView) rowView.findViewById(R.id.itemTitle);
        TextView itemDescription = (TextView) rowView.findViewById(R.id.itemDescription);
        CheckBox taskDone = (CheckBox) rowView.findViewById(R.id.doneCheckBox);

        itemTitle.setText(taskTitle.get(position));
        itemDescription.setText(taskDescription.get(position));

        return rowView;
    }
}
