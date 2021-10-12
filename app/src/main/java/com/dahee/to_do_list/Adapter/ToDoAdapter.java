package com.dahee.to_do_list.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dahee.to_do_list.Task.AddNewTask;
import com.dahee.to_do_list.Activity.MainActivity;
import com.dahee.to_do_list.Model.ToDoModel;
import com.dahee.to_do_list.R;
import com.dahee.to_do_list.Utils.TaskDatabaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    List<ToDoModel> toDoList;
    private TaskDatabaseHelper db;
    private MainActivity activity;

    public ToDoAdapter(TaskDatabaseHelper db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }
    public void onBindViewHolder(ViewHolder holder,int position){

        final ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });

        boolean isExpanded = toDoList.get(position).isExpanded();
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.expandableLayout.getVisibility()==view.GONE){
                    holder.expandableLayout.setVisibility(View.VISIBLE);
                    holder.show.setImageResource(R.drawable.ic_showless);
                } else {
                    holder.expandableLayout.setVisibility(View.GONE);
                    holder.show.setImageResource(R.drawable.ic_showmore);
                }
            }
        });
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        Log.d("TAG",item.getTask()+item.getStatus());
    }

    public int getItemCount(){
        return toDoList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTask(List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        int position = getAdapterPosition();
        CheckBox task;
        ImageButton show;
        LinearLayout expandableLayout;
        TextView delete,edit;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.cb_task);
            show = view.findViewById(R.id.btn_showmore);
            expandableLayout = view.findViewById(R.id.expandable_view);
            delete = view.findViewById(R.id.btn_del);
            edit = view.findViewById(R.id.btn_edit);

            show.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    ToDoModel toDoModel = toDoList.get(getAdapterPosition());
                    toDoModel.setExpanded(!toDoModel.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(delete.getContext());
                    builder.setTitle("Delete Task");
                    builder.setMessage("Are you sure you want to delete this Task?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(getAdapterPosition());
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editItem(getAdapterPosition());
                    show.setImageResource(R.drawable.ic_showmore);
                }
            });
//            task.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    ToDoModel toDoModel = toDoList.get(getAdapterPosition());
//                    toDoModel.setChecked(!toDoModel.isChecked());
//                    notifyItemChanged(getAdapterPosition());
//                }
//            });
        }
    }
}
