package Controllers.TaskControllers;

import Databases.ProjectsDB;
import Databases.TaskDB;
import Databases.TaskDatabase;
import Models.*;
import Models.TaskModel.SimpleTask;
import Models.TaskModel.Task;
import Models.TaskModel.TaskStatus;
import Trash.TaskComponent.TaskNotAssignedException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

public class TaskController  {
    public void checkTask(Task task){

    }

    public void createSimpleTask(String taskName, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, Duration periodicity) {
        //TODO: check if we need to pass the settings object here
        Task task = new SimpleTask(taskName, startTime, duration, priority, deadline, category, status, periodicity);
        taskDB.addTask(task);
    }
    public void checkTask(LocalTime startTime, Duration duration, LocalDate deadline, boolean isSimpleTask, Settings settings){
        //check if the task has a valid format

    }

//    public boolean isTaskOverlapping(LocalDate date, LocalTime startTime, LocalTime endTime){
//        return taskDB.isTaskOverlapping(date, startTime, endTime);
//    }

    public void planTaskManually()  {

    }

    public void planTaskAutomatically()  {

    }

    public boolean isTaskOverlapping(Task task,LocalDate date, LocalTime startTime, LocalTime endTime){
        //TODO: complete this algorithm
        if(task.getDeadline().equals(date)){

                if(task.getStartTime().isBefore(endTime) && task.getEndTime().isAfter(startTime)){
                    return true;
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


    public LocalTime getFloorTaskEndTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        return taskDB.getFloorTaskEndTime(date, startHour, startMinute, endHour, endMinute);
    }
    public LocalTime getCeilingTaskStartTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        return taskDB.getCeilingTaskStartTime(date, startHour, startMinute, endHour, endMinute);
    }

}
