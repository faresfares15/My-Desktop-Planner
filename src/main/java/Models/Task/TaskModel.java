package Models.Task;

import Databases.TaskDatabase;
import Exceptions.DayDoesNotHaveTasksException;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskModel {
    private TaskDatabase taskDatabase;

    public TaskModel(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException {
        return this.taskDatabase.findMany(date);
    }
}
/* Your code
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

 */
