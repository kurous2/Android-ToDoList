package com.dahee.to_do_list.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dahee.to_do_list.Adapter.ToDoAdapter;
import com.dahee.to_do_list.Model.ToDoModel;
import com.dahee.to_do_list.R;
import com.dahee.to_do_list.Task.AddNewTask;
import com.dahee.to_do_list.Utils.Session;
import com.dahee.to_do_list.Utils.TaskDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private TaskDatabaseHelper db;

    LinearLayout expandableView;
    ImageButton imageButton;
    CardView cardView;
    TextView tv_email;
    ImageButton btnLogout;
    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private Session session;
    String email;
    List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

        taskList = new ArrayList<>();

        db = new TaskDatabaseHelper(this);
        db.openDatabase();

        taskRecyclerView = findViewById(R.id.listview);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(db,this);
        taskRecyclerView.setAdapter(taskAdapter);

        tv_email = findViewById(R.id.tv_email);
        btnLogout = findViewById(R.id.btn_logout);
        expandableView = findViewById(R.id.expandable_view);
        imageButton = findViewById(R.id.btn_showmore);
        cardView = findViewById(R.id.cardview_expandable);

        fab = findViewById(R.id.fab);
        email = session.prefs.getString("EMAIL","");
        tv_email.setText(email);
        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        taskAdapter.setTask(taskList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);
        taskAdapter.notifyDataSetChanged();
    }

    private void logout(){
        session.setLoggedin(false,email);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}