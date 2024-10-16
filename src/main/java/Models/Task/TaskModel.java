package Models.Task;

import Databases.TaskDatabase;
import Databases.TaskFileDatabase;
import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import esi.tp_poo_final.HelloApplication;
import javafx.concurrent.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class TaskModel {
    private TaskDatabase taskDatabase;

    public TaskModel(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }
    public void save() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.taskDbFileName))) {
            objectOutputStream.writeObject(taskDatabase);
        }
    }
    public void load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.taskDbFileName))) {
            taskDatabase = (TaskFileDatabase) objectInputStream.readObject();
        }
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
    public TaskSchema find(LocalDate date, int id) throws TaskDoesNotExistException {
        return this.taskDatabase.find(date, id);
    }
    public TaskSchema find(int id) throws TaskDoesNotExistException{
        return this.taskDatabase.find(id);
    }
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findAll(){
        return this.taskDatabase.findAll();
    }
    public ArrayList<TaskSchema> findAll(String taskType){
        return this.taskDatabase.findAll(taskType);
    }
    public void update(LocalDate date, int id, String newName) throws TaskDoesNotExistException {
            this.taskDatabase.update(date, id, newName);
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
