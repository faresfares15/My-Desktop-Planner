package Models.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class TaskModel {
    private ArrayList<TaskSchema> tasks = new ArrayList<>();
    //It will be sorteed acording to the ID everyTime an operation on it will be done it.

    public TaskModel(ArrayList<TaskSchema> tasks) {
        this.tasks = tasks;
    }

    public TaskModel() {
    }
    //CRUD Operations
    public void create(TaskSchema taskSchema){
        tasks.add(taskSchema);
        Collections.sort(tasks);
    }
    public TaskSchema read(int id){
        for (TaskSchema taskSchema:
             tasks) {
            if (taskSchema.getId() == id) return taskSchema;
        }
        return null;
    }
    public void update (TaskSchema taskSchema){
        for (TaskSchema oldTask:
                tasks) {
            if (oldTask.getId() == taskSchema.getId()){
                tasks.remove(oldTask);
                tasks.add(taskSchema);
                Collections.sort(tasks);
                return;
            }
        }
    }
    public void delete(TaskSchema taskSchema){
        tasks.remove(taskSchema);
        Collections.sort(tasks);
    }
}
