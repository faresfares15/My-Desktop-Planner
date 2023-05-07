package Controllers;

import Databases.ProjectsDB;
import Databases.TaskDB;
import Models.*;
import TaskComponent.TaskNotAssignedException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TaskScheduler implements TaskSchedule {
    private TaskDB taskDB = new TaskDB();
    private ProjectsDB projectsDB;
    public void checkTask(Task task){

    }

    @Override
    public void createSimpleTask(String taskName, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, Duration periodicity) {
        //TODO: check if we need to pass the settings object here
        Task task = new SimpleTask(taskName, startTime, duration, priority, deadline, category, status, periodicity);
        taskDB.addTask(task);
    }
    public void checkTask(LocalTime startTime, Duration duration, LocalDate deadline, boolean isSimpleTask, Settings settings){
        //check if the task has a valid format

    }

    public boolean isTaskOverlapping(LocalDate date, LocalTime startTime, LocalTime endTime){
        return taskDB.isTaskOverlapping(date, startTime, endTime);
    }

    @Override
    public void planTaskManually() throws TaskNotAssignedException {

    }

    @Override
    public void planTaskAutomatically() throws TaskNotAssignedException {

    }

    @Override
    public void createProject() {

    }
    public LocalTime getFloorTaskEndTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        return taskDB.getFloorTaskEndTime(date, startHour, startMinute, endHour, endMinute);
    }
    public LocalTime getCeilingTaskStartTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute){
        return taskDB.getCeilingTaskStartTime(date, startHour, startMinute, endHour, endMinute);
    }
}
