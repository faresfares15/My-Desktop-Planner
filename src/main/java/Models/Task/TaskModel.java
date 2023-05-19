package Models.Task;

import Databases.TaskDatabase;
import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class TaskModel {
    private TaskDatabase taskDatabase;

    public TaskModel(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    //CRUD Operations

    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException {
        return this.taskDatabase.findMany(date);
    }
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findMany(LocalDate startDate, LocalDate endDate){
        return this.taskDatabase.findMany(startDate, endDate);
    }
    public TaskSchema create(TaskSchema taskSchema){
        return this.taskDatabase.create(taskSchema);
    }
    public void initialize(LocalDate date){
        this.taskDatabase.initialize(date);
    }
    public TaskSchema read(int id, LocalDate date) throws TaskDoesNotExistException {
        return this.taskDatabase.read(id, date);
    }
    public TaskSchema find(LocalDate date, String name) throws TaskDoesNotExistException {
        return this.taskDatabase.find(date, name);
    }
    public void update(TaskSchema taskSchema) throws TaskDoesNotExistException {
            this.taskDatabase.update(taskSchema);
    }
    public void delete(TaskSchema taskSchema) throws TaskDoesNotExistException {
            this.taskDatabase.delete(taskSchema);

    }
}
/* Your code
public class TaskModel {
    private ArrayList<TaskSchema> tasks = new ArrayList<>();
    //It will be sorted according to the ID everyTime an operation on it will be done it.

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
