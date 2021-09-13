package com.dahee.to_do_list;

import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dahee.to_do_list.Adapter.ToDoAdapter;
import com.dahee.to_do_list.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout expandableView;
    ImageButton imageButton;
    CardView cardView;
    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;

    List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.listview);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        expandableView = findViewById(R.id.expandable_view);
        imageButton = findViewById(R.id.btn_showmore);
        cardView = findViewById(R.id.cardview_expandable);

        fab = findViewById(R.id.fab);

        ToDoModel task = new ToDoModel();
        task.setTask("Thisis a test Task");

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);


        taskAdapter.setTask(taskList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void showMore(View view){
//        if(expandableView.getVisibility() == View.GONE){
//            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
//            expandableView.setVisibility(View.VISIBLE);
//            imageButton.setRotation(imageButton.getRotation()+180);
//        }else {
//            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
//            expandableView.setVisibility(View.GONE);
//            imageButton.setRotation(imageButton.getRotation()+180);
//        }
//    }
}