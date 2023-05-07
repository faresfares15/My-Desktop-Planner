package Databases;

import Models.TaskModel.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class TaskDB  {
    private TreeMap<LocalDate, ArrayList<Task>> tasks;
    public TaskDB(){
        this.tasks = new TreeMap<>();
    }

    public void addTask(Task task){
        //TODO: add start time to the created task
        if (tasks.containsKey(task.getDeadline())) {
            tasks.get(task.getDeadline()).add(task);
        } else {
            ArrayList<Task> tasksList = new ArrayList<>();
            tasksList.add(task);
            tasks.put(task.getDeadline(), tasksList);
        }
    }
    public TreeMap<LocalTime, LocalTime> getTasks(LocalDate date){
        TreeMap<LocalTime, LocalTime> tasksMap = new TreeMap<>();
        if(tasks.containsKey(date)){
            for(Task task : tasks.get(date)){
                tasksMap.put(task.getStartTime(), task.getEndTime());
            }
        }
        return tasksMap;
    }

}
