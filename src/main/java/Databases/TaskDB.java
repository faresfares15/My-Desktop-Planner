package Databases;

import Models.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class TaskDB implements TaskDatabase {
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
    public boolean isTaskOverlapping(LocalDate date, LocalTime startTime, LocalTime endTime){
        //TODO: complete this algorithm
        if(tasks.containsKey(date)){
            for(Task task : tasks.get(date)){
                if(task.getStartTime().isBefore(endTime) && task.getEndTime().isAfter(startTime)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean isTaskIntervalsOverlapping(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        if(tasks.containsKey(date)){
            for(Task task : tasks.get(date)){
                task.contains(startHour, startMinute, endHour, endMinute);
                    return true;
            }
        }
        return false;
    }
    public LocalTime getFloorTaskEndTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        LocalTime floorTaskEndTime = null;

        if(tasks.containsKey(date)){
            for(Task task : tasks.get(date)){
                if(task.getEndTime().isBefore(LocalTime.of(startHour, startMinute))){
                    floorTaskEndTime = task.getStartTime().plus(task.getDuration());
                }
            }
            return floorTaskEndTime;
        }
        return null;
    }
    public LocalTime getCeilingTaskStartTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        LocalTime ceilingTaskStartTime = null;

        if(tasks.containsKey(date)){
            for(Task task : tasks.get(date)){
                if(task.getStartTime().isAfter(LocalTime.of(endHour, endMinute))){
                    ceilingTaskStartTime = task.getStartTime();
                }
            }
            return ceilingTaskStartTime;
        }
        return null;
    }
}
