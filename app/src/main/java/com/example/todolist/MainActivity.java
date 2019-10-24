package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    }



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyListAdapter adapter = new MyListAdapter(this, tasks, descriptions,finished);
        list = (ListView)findViewById(R.id.toDoListView);
        list.setAdapter(adapter);

        Button addBtn = (Button)findViewById(R.id.addBtn);




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
                    }
                });

                // TODO: Add shadowing
                //popupWindow.setElevation(20);

            }
        });

    }


}
