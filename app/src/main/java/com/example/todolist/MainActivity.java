package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    static ArrayList<String> descriptions = new ArrayList<>();
    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayList<Boolean> finished = new ArrayList<>();
    static int maxTasks = 10;



    // add item
    public static void enterItem(String title, String description) {
        if (tasks.size() < maxTasks) {
            tasks.add(title);
            descriptions.add(description);
            finished.add(false);
        }
        // TODO: Stop user from entering empty task
    }

    public static void saveList (Context context) {
        // Open preferences file for today's list
        SharedPreferences sharedPrefTasks = context.getSharedPreferences(
                "currentTasks", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefDescriptions = context.getSharedPreferences(
                "currentDescriptions", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefTasksDone = context.getSharedPreferences(
                "currentTasksDone", Context.MODE_PRIVATE);


        // Write to preferences
        SharedPreferences.Editor tasksEditor = sharedPrefTasks.edit();
        tasksEditor.clear();
        tasksEditor.apply();
        for (int i = 0; i < tasks.size(); i++) {
            tasksEditor.putString(String.valueOf(i), tasks.get(i));
            tasksEditor.apply();
        }
        SharedPreferences.Editor descriptionsEditor = sharedPrefDescriptions.edit();
        descriptionsEditor.clear();
        descriptionsEditor.apply();
        for (int i = 0; i < tasks.size(); i++) {
            descriptionsEditor.putString(String.valueOf(i), descriptions.get(i));
            descriptionsEditor.apply();
        }
        SharedPreferences.Editor tasksDoneEditor = sharedPrefTasksDone.edit();
        tasksDoneEditor.clear();
        tasksDoneEditor.apply();
        for (int i = 0; i < tasks.size(); i++) {
            tasksDoneEditor.putString(String.valueOf(i), finished.get(i).toString());
            tasksDoneEditor.apply();
        }

    }

    public static ArrayList<ArrayList> getSavedList (Context context) {
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<String> taskDescriptions = new ArrayList<>();
        ArrayList<Boolean> tasksDone = new ArrayList<>();

        // Arraylist of arraylists to be returned by the method
        ArrayList<ArrayList> joinedTasks = new ArrayList<>();


        SharedPreferences sharedPrefTasks = context.getSharedPreferences(
                "currentDescriptions", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefDescriptions = context.getSharedPreferences(
                "currentDescriptions", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefTasksDone = context.getSharedPreferences(
                "currentTasksDone", Context.MODE_PRIVATE);

        System.out.println(sharedPrefTasks.getAll());
        System.out.println(sharedPrefDescriptions.getAll());
        System.out.println(sharedPrefTasksDone.getAll());


        /*
        for (int i = 0; i < tasks.size(); i++) {
            if (getResources().getString())
        }
        */

        return joinedTasks;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyListAdapter adapter = new MyListAdapter(this, tasks, descriptions,finished);
        list = (ListView)findViewById(R.id.toDoListView);
        list.setAdapter(adapter);

        Button addBtn = (Button)findViewById(R.id.addBtn);
        Button getPrefsBtn = (Button)findViewById(R.id.getPrefsBtn);


        //adapter.notifyDataSetChanged();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.entrypopup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);



                // show the popup window
                // which view you pass in doesnt matter, it is only used for the window token
                popupWindow.showAtLocation(v, Gravity.CENTER, 0,0);

                Button submitBtn = (Button)popupView.findViewById(R.id.submitBtn);
                final EditText taskName = (EditText)popupView.findViewById(R.id.taskName);
                final EditText taskDescription = (EditText)popupView.findViewById(R.id.taskDescription);


                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = taskName.getText().toString();
                        String description = taskDescription.getText().toString();
                        MainActivity.enterItem(name, description);
                        adapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                        saveList(v.getContext());
                    }
                });

                // TODO: Add shadowing
                //popupWindow.setElevation(20);

            }
        });

        getPrefsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSavedList(v.getContext());
            }
        });
    }


}
