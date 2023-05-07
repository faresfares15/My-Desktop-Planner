package Controllers;

import Models.Priority;
import Models.TaskStatus;
import TaskComponent.TaskNotAssignedException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

public interface TaskManager {
    //TODO: add methods
//    public void createTask(Task task, Settings settings) throws TaskNotAssignedException;
    public TreeMap<LocalTime, LocalTime> getTasks(LocalDate date);
    public void createSimpleTask(String taskName, LocalTime startTime, Duration duration, Priority priority,
                                 LocalDate deadline, String category, TaskStatus status, Duration periodicity);
    public void planTaskManually() throws TaskNotAssignedException;
    public void planTaskAutomatically() throws TaskNotAssignedException;
    public void createProject();

    public boolean isTaskOverlapping(LocalDate date, LocalTime startTime, LocalTime endTime);

    LocalTime getFloorTaskEndTime(LocalDate of, int startHour, int startMinute, int endHour, int endMinute);

    LocalTime getCeilingTaskStartTime(LocalDate of, int startHour, int startMinute, int endHour, int endMinute);
}
