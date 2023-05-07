package Models.TaskModel;

import Models.TaskModel.SimpleTask;
import Models.TaskModel.Task;

import java.util.ArrayList;

public class ComposedTask extends Task {
    private ArrayList<SimpleTask> subTasks;

    public ComposedTask(ArrayList<SimpleTask> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<SimpleTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SimpleTask> subTasks) {
        this.subTasks = subTasks;
    }
}
