package Models.TaskModel;

import Models.Settings;
import Models.TaskModel.SimpleTask;
import Models.TaskModel.Task;

import java.util.ArrayList;

public class ComposedTask extends Task {
    private ArrayList<SimpleTask> subTasks;

    public ComposedTask(ArrayList<SimpleTask> subTasks) {
        this.subTasks = subTasks;
    }
    public ComposedTask(SimpleTask task) {
        //The one we'll be using I think
        this.subTasks = new ArrayList<SimpleTask>();
        this.subTasks.add(task);
    }

    public ArrayList<SimpleTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SimpleTask> subTasks) {
        this.subTasks = subTasks;
    }

    public void manualPlanTask(Settings settings, int month, Day day, int startHour, int startMinute, int endHour, int endMinute){
        //Things needed by this method will be passed as parameters temporally until we figure were to get them from

    }
}
