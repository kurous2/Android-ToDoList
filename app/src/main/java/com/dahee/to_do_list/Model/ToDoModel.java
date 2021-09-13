package com.dahee.to_do_list.Model;

public class ToDoModel {
    String task;
    boolean isExpanded,isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
